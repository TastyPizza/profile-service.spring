package ru.tasty_pizza.profile_service_spring.service

import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.tasty_pizza.profile_service_spring.dto.request.NewClient
import ru.tasty_pizza.profile_service_spring.dto.response.AuthResponse
import ru.tasty_pizza.profile_service_spring.entity.Client
import ru.tasty_pizza.profile_service_spring.repo.ClientRepository
import java.util.*

@Service
class ClientService(
    private val clientRepository: ClientRepository,
    private val verificationCodeGenerator: VerificationCodeGenerator,
    private val mailService: MailService
) {

    fun findClient(email: String) = clientRepository.findByEmail(email)
    
    fun isUserPresent(email: String) = findClient(email).isPresent
    
    @Transactional
    fun createClient(clientRequest: NewClient): ResponseEntity<AuthResponse> {
        if (isUserPresent(clientRequest.email)){
            return ResponseEntity<AuthResponse>(
                AuthResponse(errorMessage = "Пользователь уже существует"),
                HttpStatus.CONFLICT
            )
        }
        
        val client = Client (
            email = clientRequest.email,
            verificationCode = verificationCodeGenerator.generate(),
            isMale = clientRequest.isMale,
            firstName = clientRequest.firstName,
            lastName = clientRequest.lastName,
            phoneNumber = clientRequest.phoneNumber,
            birthday = java.sql.Date.valueOf(clientRequest.birthday)
        )

        clientRepository.save(client)
        
        return mailService.sendVerificationCode(client.email, client.verificationCode)
    }
    
    @Transactional
    fun sendVerificationCode(email: String): ResponseEntity<AuthResponse>{
        val client = clientRepository.findByEmail(email)
        
        if (client.isEmpty){
            return ResponseEntity<AuthResponse>(
                AuthResponse(errorMessage = "Пользователь не существует"),
                HttpStatus.NOT_FOUND
            )
        }
        
        client.get().verificationCode = verificationCodeGenerator.generate()
        clientRepository.save(client.get())

        return mailService.sendVerificationCode(client.get().email, client.get().verificationCode)
    }

    fun getProfile(): ResponseEntity<Client> {
        val email = SecurityContextHolder.getContext().authentication.name
        val client = clientRepository.findByEmail(email)
        
        if (client.isEmpty) return ResponseEntity(null, HttpStatus.NOT_FOUND)  
        
        return ResponseEntity(client.get(), HttpStatus.OK)
    }

    fun findByEmail(email: String): Optional<Client> = clientRepository.findByEmail(email)
}