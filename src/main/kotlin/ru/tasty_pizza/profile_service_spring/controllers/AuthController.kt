package ru.tasty_pizza.profile_service_spring.controllers

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.tasty_pizza.profile_service_spring.dto.request.NewClient
import ru.tasty_pizza.profile_service_spring.service.AuthService
import ru.tasty_pizza.profile_service_spring.validators.annotations.EmailConstraint

@RestController
@RequestMapping("auth")
class AuthController (
    private val authService: AuthService
) {
    @PostMapping("sign-in")
    fun signIn(@RequestParam @EmailConstraint @Valid email: String) = authService.signIn(email)

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody @Valid newClient: NewClient) = authService.signUp(newClient)

    @PostMapping("verification")
    fun verify(@RequestParam @NotBlank verificationCode: String) = authService.verify(verificationCode)

    @PostMapping("refresh-token")
    fun refreshToken(@RequestParam id: String) = authService.refreshToken(id)
}