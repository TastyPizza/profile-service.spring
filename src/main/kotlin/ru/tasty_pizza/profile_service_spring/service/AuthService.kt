package ru.tasty_pizza.profile_service_spring.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import ru.tasty_pizza.profile_service_spring.dto.request.NewClient
import ru.tasty_pizza.profile_service_spring.dto.response.VerificationResponse
import ru.tasty_pizza.profile_service_spring.security.JwtTokenProvider

@Service
class AuthService (
    private val clientService: ClientService,
    private val tokenProvider: JwtTokenProvider
) {
    fun signIn(email: String) = clientService.sendVerificationCode(email)
    fun signUp(newClient: NewClient) = clientService.createClient(newClient)
    

    fun verify(verificationCode: String): ResponseEntity<VerificationResponse>  {
        val email = SecurityContextHolder.getContext().authentication.name
        val client = clientService.findByEmail(email)

        if (client.isEmpty) return ResponseEntity(null, HttpStatus.NOT_FOUND)
        
        if (verificationCode != client.get().verificationCode){
            return ResponseEntity(
                VerificationResponse(errorMessage = "Некорректный код"),
                HttpStatus.BAD_REQUEST
            )
        }
        
        val response = VerificationResponse(
            tokenProvider.generateAccessToken(email),
            tokenProvider.generateRefreshToken(email)
        )
        
        return ResponseEntity(response, HttpStatus.OK)
    }

    fun refreshToken(id: String): ResponseEntity<VerificationResponse> {
        if (StringUtils.hasText(id) &&
            tokenProvider.validateToken(id) &&
            tokenProvider.getTokenType(id) == "refresh"
        ){
            val email = SecurityContextHolder.getContext().authentication.name
            val response = VerificationResponse(
                tokenProvider.generateAccessToken(email),
                tokenProvider.generateRefreshToken(email)
            )

            return ResponseEntity(response, HttpStatus.OK)
        }

        return ResponseEntity(
            VerificationResponse(errorMessage = "Требуется корректный refresh token"),
            HttpStatus.BAD_REQUEST
        )
    }
}