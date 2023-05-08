package ru.tasty_pizza.profile_service_spring.validators.annotations

import jakarta.validation.Constraint
import jakarta.validation.Payload
import ru.tasty_pizza.profile_service_spring.validators.PhoneNumberValidator
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PhoneNumberValidator::class])
annotation class PhoneNumberConstraint (
    val message: String = "Некорректный номер телефона",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)