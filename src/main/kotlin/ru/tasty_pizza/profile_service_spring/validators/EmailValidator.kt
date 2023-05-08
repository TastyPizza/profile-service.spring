package ru.tasty_pizza.profile_service_spring.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import ru.tasty_pizza.profile_service_spring.validators.annotations.EmailConstraint
import java.util.regex.Pattern

class EmailValidator  : ConstraintValidator<EmailConstraint, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return Pattern
            .compile("^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.\\-]+@[a-zA-Z0-9.\\-]+$")
            .matcher(value)
            .find()
    }
}