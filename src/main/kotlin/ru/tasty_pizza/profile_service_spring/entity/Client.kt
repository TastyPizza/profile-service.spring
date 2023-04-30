package ru.tasty_pizza.profile_service_spring.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.sql.Date
import java.time.LocalDateTime

@Entity
@Table(name = "client")
data class Client (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int = 0,

    @Column(name = "email", nullable = false)
    var email: String,

    /*    @Column(name = "is_activated", nullable = false)
        var isActivated: Boolean? = false,*/

    @Column(name = "last_verification_code", nullable = false)
    var verificationCode: String = "0000",

    @Column(name = "is_male", nullable = false)
    var isMale: Boolean? = false,

    @Column(name = "first_name", nullable = false)
    var firstName: String? = null,

    @Column(name = "last_name", nullable = false)
    var lastName: String? = null,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String? = null,

    @Column(name = "birthday", nullable = false)
    var birthday: Date? = null,

    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    private var creationDate: LocalDateTime? = null,

    @Column(name = "tasty_coins", nullable = false)
    var tastyCoins: Int? = 0
)