package ru.tasty_pizza.profile_service_spring.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import ru.tasty_pizza.profile_service_spring.validators.annotations.PhoneNumberConstraint
import java.util.regex.Pattern

class PhoneNumberValidator : ConstraintValidator<PhoneNumberConstraint, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return Pattern
            .compile("^(\\+?\\d{1,3}[- ]?)?\\d{10}$")
            .matcher(value)
            .find()
    }
}