package ru.tasty_pizza.profile_service_spring.validators.annotations

import jakarta.validation.Constraint
import jakarta.validation.Payload
import ru.tasty_pizza.profile_service_spring.validators.BirthdayValidator
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [BirthdayValidator::class])
annotation class BirthdayConstraint (
    val message: String = "Некорректная дата рождения",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)