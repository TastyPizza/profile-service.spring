package ru.tasty_pizza.profile_service_spring.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.tasty_pizza.profile_service_spring.dto.response.PublicKey
import ru.tasty_pizza.profile_service_spring.security.JwtTokenProvider

@RestController
@RequestMapping("public_key")
class PublicKeysController (
    private val jwtTokenProvider: JwtTokenProvider
) {
    @GetMapping
    fun getPublicKey() = PublicKey(jwtTokenProvider.publicKey.encoded)
}