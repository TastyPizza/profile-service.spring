package ru.tasty_pizza.profile_service_spring.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.tasty_pizza.profile_service_spring.dto.request.NewClient
import ru.tasty_pizza.profile_service_spring.service.AuthService

@RestController
@RequestMapping("auth")
class AuthController (
    private val authService: AuthService
) {
    @PostMapping("sign-in")
    fun signIn(@RequestParam email: String) = authService.signIn(email)

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody newClient: NewClient) = authService.signUp(newClient)

    @PostMapping("verification")
    fun verify(@RequestParam verificationCode: String) = authService.verify(verificationCode)

    @PostMapping("refresh-token")
    fun refreshToken(@RequestParam id: String) = authService.refreshToken(id)
}