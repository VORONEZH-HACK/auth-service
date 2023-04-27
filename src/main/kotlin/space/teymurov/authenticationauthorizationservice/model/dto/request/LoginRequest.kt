package space.teymurov.authenticationauthorizationservice.model.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class LoginRequest(
    @field:NotBlank
    @field:Email
    @field:Length(min = 3, max = 20)
    val email: String,

    @field:NotBlank
    @field:Length(min = 5, max = 10)
    val password: String
)
