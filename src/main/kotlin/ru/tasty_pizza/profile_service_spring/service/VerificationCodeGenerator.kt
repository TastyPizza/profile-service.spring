package ru.tasty_pizza.profile_service_spring.service

import org.springframework.stereotype.Service
import kotlin.math.floor

@Service
class VerificationCodeGenerator {
    fun generate(): String {
        val token = StringBuilder()

        for (i in 0..3) {
            token.append(
                floor(Math.random() * 10).toInt()
            )
        }

        return token.toString()
    }
}