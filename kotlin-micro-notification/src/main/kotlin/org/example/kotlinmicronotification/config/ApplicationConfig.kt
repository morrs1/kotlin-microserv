package org.example.kotlinmicronotification.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.example.kotlinmicronotification.dto.UsersOrder
import org.example.kotlinmicronotification.utils.MailSender
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.JacksonUtils
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor


@Configuration
class ApplicationConfig() {

    companion object {
        private val log = LoggerFactory.getLogger(ApplicationConfig::class.java)
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return JacksonUtils.enhancedObjectMapper()
    }

    @Bean
    fun consumerFactory(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper
    ): ConsumerFactory<String, UsersOrder> {
        val props = kafkaProperties.buildConsumerProperties()
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[TYPE_MAPPINGS] =
            "org.example.kotlinmicroorder.dto.UsersOrder:org.example.kotlinmicronotification.dto.UsersOrder"
        props[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = 3
        props[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = 3_000
        val kafkaConsumerFactory = DefaultKafkaConsumerFactory<String, UsersOrder>(props)
        kafkaConsumerFactory.setValueDeserializer(JsonDeserializer(objectMapper))
        return kafkaConsumerFactory
    }

    @Bean("listenerContainerFactory")
    fun listenerContainerFactory(
        consumerFactory: ConsumerFactory<String, UsersOrder>
    ): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UsersOrder>> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, UsersOrder> =
            ConcurrentKafkaListenerContainerFactory<String, UsersOrder>()
        factory.consumerFactory = consumerFactory
        factory.isBatchListener = true
        factory.setConcurrency(1)
        factory.containerProperties.idleBetweenPolls = 1_000
        factory.containerProperties.pollTimeout = 1_000

        val executor = SimpleAsyncTaskExecutor("k-consumer-")
        executor.concurrencyLimit = 10
        val listenerTaskExecutor = ConcurrentTaskExecutor(executor)
        factory.containerProperties.listenerTaskExecutor = listenerTaskExecutor
        return factory
    }

    @Bean
    fun stringValueConsumer(mailSender: MailSender): KafkaClient {
        return KafkaClient(mailSender)
    }

    class KafkaClient(private val mailSender: MailSender) {
        @KafkaListener(topics = ["\${application.kafka.topic}"], containerFactory = "listenerContainerFactory")
        fun listen(@Payload values: List<UsersOrder>) {
            log.info("values, values.size:{}", values.size)
            values.forEach { value -> log.info(value.toString()) }
        }
    }
}