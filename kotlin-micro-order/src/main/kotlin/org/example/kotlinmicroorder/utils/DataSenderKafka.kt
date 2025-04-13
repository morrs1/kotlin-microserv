package org.example.kotlinmicroorder.utils

import lombok.extern.slf4j.Slf4j
import org.example.kotlinmicroorder.dto.UsersOrder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate

@Slf4j
class DataSenderKafka(private val topic: String, private val template: KafkaTemplate<String, UsersOrder>) : DataSender {
    private val logger: Logger = LoggerFactory.getLogger(DataSenderKafka::class.java)

    override fun send(usersOrder: UsersOrder) {
        try {
            logger.info("value:{}", usersOrder)
            template.send(topic, usersOrder)
                .whenComplete { result, ex ->
                    if (ex == null) {
                        logger.info(
                            "message id:{} was sent, offset:{}",
                            usersOrder.id,
                            result.recordMetadata.offset()
                        )
                    } else {
                        logger.error("message id:{} was not sent", usersOrder.id, ex)
                    }
                }
        } catch (ex: Exception) {
            logger.error("send error, value:{}", usersOrder, ex)
        }
    }
}