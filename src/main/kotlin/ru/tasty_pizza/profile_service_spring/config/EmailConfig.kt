package ru.tasty_pizza.profile_service_spring.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
@ConfigurationProperties
class EmailConfig (
    @Value("\${spring.mail.host}") 
    private val host: String,

    @Value("\${spring.mail.port}") 
    private val port: Int,
    
    @Value("\${spring.mail.username}") 
    private val user: String,
    
    @Value("\${spring.mail.password}") 
    private val password: String,
    
    @Value("\${spring.mail.protocol}")
    private val protocol: String,
    
    @Value("\${mail.debug}")
    private val debug: Boolean
) {

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        
        mailSender.host = host
        mailSender.port = port
        mailSender.username = user
        mailSender.password = password
        
        val props = mailSender.javaMailProperties
        
        props["mail.transport.protocol"] = protocol
        props["mail.smtp.auth"] = "true"
        props["mail.debug"] = debug
        
        return mailSender
    }
}