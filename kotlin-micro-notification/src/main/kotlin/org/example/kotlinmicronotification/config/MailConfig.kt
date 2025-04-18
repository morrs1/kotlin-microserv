package org.example.kotlinmicronotification.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*


@Configuration
class MailConfig(
    @Value("\${spring.mail.host}") private val host: String,
    @Value("\${spring.mail.username}") private val username: String,
    @Value("\${spring.mail.password}") private val password: String,
    @Value("\${spring.mail.port}") private val port: Int,
    @Value("\${spring.mail.protocol}") private val protocol: String,
    @Value("\${mail.debug}") private val debug: String
) {

    @Bean
    fun getMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()

        mailSender.host = host
        mailSender.port = port
        mailSender.username = username
        mailSender.password = password

        val properties: Properties = mailSender.javaMailProperties

        properties.setProperty("mail.transport.protocol", protocol)
        properties.setProperty("mail.debug", debug)

        return mailSender
    }
}