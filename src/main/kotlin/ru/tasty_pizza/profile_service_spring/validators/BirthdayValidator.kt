package ru.tasty_pizza.profile_service_spring.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import ru.tasty_pizza.profile_service_spring.validators.annotations.BirthdayConstraint
import java.util.regex.Pattern

class BirthdayValidator : ConstraintValidator<BirthdayConstraint, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return Pattern
            .compile("^[12][8901][0-9]{2}-[01][0-9]-[0-3][0-9]$")
            .matcher(value)
            .find()
    }
}