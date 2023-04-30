package ru.tasty_pizza.profile_service_spring.dto.response

data class VerificationResponse (
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var errorMessage: String? = null
)