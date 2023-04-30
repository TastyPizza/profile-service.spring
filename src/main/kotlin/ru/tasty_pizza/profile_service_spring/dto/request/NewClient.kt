package ru.tasty_pizza.profile_service_spring.dto.request

data class NewClient (
    val email: String,
    val isMale: Boolean = true,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val birthday: String = "1970-01-01"
)