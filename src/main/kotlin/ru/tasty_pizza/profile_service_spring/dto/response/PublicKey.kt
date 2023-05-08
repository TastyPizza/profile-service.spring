package ru.tasty_pizza.profile_service_spring.dto.response

import java.io.Serializable

data class PublicKey (
    val encoded: ByteArray
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublicKey

        return encoded.contentEquals(other.encoded)
    }

    override fun hashCode(): Int {
        return encoded.contentHashCode()
    }
}