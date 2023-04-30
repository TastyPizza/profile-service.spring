package ru.tasty_pizza.profile_service_spring.service

import jakarta.mail.MessagingException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.tasty_pizza.profile_service_spring.dto.response.AuthResponse
import ru.tasty_pizza.profile_service_spring.security.JwtTokenProvider

@Service
class MailService (
    @Value("\${spring.mail.username}") 
    private val fromUser: String,
    private val javaMailSender: JavaMailSender,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager
) {

    private val CONFIRMATION_SUBJECT = "Код подтверждения TastyPizza"
    private val log = LoggerFactory.getLogger(this.javaClass)
    
    @Async
    fun sendVerificationCode(email: String, verificationCode: String): ResponseEntity<AuthResponse> {
        val mimeMessage = javaMailSender.createMimeMessage()
        val message = "<b>$verificationCode</b> - ваш код для авторизации в TastyPizza."
        var result: ResponseEntity<AuthResponse> = ResponseEntity(AuthResponse(jwt = jwtTokenProvider.generateReadyToGetVerifiedToken(email)), HttpStatus.OK)

        try {
            val helper = MimeMessageHelper(mimeMessage, true, "utf-8")
            helper.setFrom(fromUser)
            helper.setTo(email)
            helper.setSubject(CONFIRMATION_SUBJECT)
            helper.setText(message, true)
            
            javaMailSender.send(mimeMessage)

            // Sign in
/*            SecurityContextHolder.getContext().authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    email,
                    "",
                    setOf(SimpleGrantedAuthority("ROLE_UNVERIFIED_CLIENT"))
                )
            )*/
            SecurityContextHolder.getContext().authentication.isAuthenticated = true
            
        } catch (e: MessagingException) {
            result = ResponseEntity(AuthResponse(errorMessage = e.message), HttpStatus.INTERNAL_SERVER_ERROR)
            log.error("Failed to send email to $email, due to: ${e.message}")
        }

        return result
    }
}