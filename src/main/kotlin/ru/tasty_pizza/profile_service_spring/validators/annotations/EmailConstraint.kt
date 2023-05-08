package ru.tasty_pizza.profile_service_spring.validators.annotations

import jakarta.validation.Constraint
import jakarta.validation.Payload
import ru.tasty_pizza.profile_service_spring.validators.EmailValidator
import kotlin.reflect.KClass
@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailValidator::class])
annotation class EmailConstraint (
    val message: String = "Некорректный адрес электронной потчы",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)