package org.example.kotlinmicroorder.config


import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.example.kotlinmicroorder.dto.UsersOrder
import org.example.kotlinmicroorder.utils.DataSender
import org.example.kotlinmicroorder.utils.DataSenderKafka
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.JacksonUtils
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class ApplicationConfig(@Value("\${application.kafka.topic}") val topicName: String) {

    @Bean
    fun objectMapper(): ObjectMapper {
        return JacksonUtils.enhancedObjectMapper()
    }

    @Bean
    fun producerFactory(kafkaProperties: KafkaProperties, mapper: ObjectMapper): ProducerFactory<String, UsersOrder> {
        val props = kafkaProperties.buildProducerProperties()
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        val kafkaProducerFactory: DefaultKafkaProducerFactory<String, UsersOrder> = DefaultKafkaProducerFactory<String, UsersOrder>(props)
        kafkaProducerFactory.valueSerializer = JsonSerializer(mapper)

        return kafkaProducerFactory
    }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, UsersOrder>): KafkaTemplate<String, UsersOrder> {
        return KafkaTemplate(producerFactory)
    }

    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build()
    }

    @Bean
    fun dataSender(topic: NewTopic, kafkaTemplate: KafkaTemplate<String, UsersOrder>): DataSender {
        return DataSenderKafka(topic.name(), kafkaTemplate)
    }


}