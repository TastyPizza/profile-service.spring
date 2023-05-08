package ru.tasty_pizza.profile_service_spring.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import ru.tasty_pizza.profile_service_spring.validators.annotations.BirthdayConstraint
import ru.tasty_pizza.profile_service_spring.validators.annotations.PhoneNumberConstraint
import ru.tasty_pizza.profile_service_spring.validators.annotations.EmailConstraint

data class NewClient (
    @EmailConstraint
    @NotBlank
    val email: String,
    
    @NotNull
    val isMale: Boolean = true,
    
    @Size(min=2, max=50)
    val firstName: String,
    
    @Size(min=2, max=50)
    val lastName: String,
    
    @PhoneNumberConstraint
    val phoneNumber: String,
    
    @BirthdayConstraint
    val birthday: String = "1970-01-01"
)