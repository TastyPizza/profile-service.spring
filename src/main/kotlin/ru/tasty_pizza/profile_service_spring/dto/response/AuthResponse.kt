package ru.tasty_pizza.profile_service_spring.dto.response

data class AuthResponse (
    var jwt: String? = null,
    var errorMessage: String? = null
)