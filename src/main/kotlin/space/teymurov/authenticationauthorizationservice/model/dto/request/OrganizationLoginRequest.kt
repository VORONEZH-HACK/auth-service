package space.teymurov.authenticationauthorizationservice.model.dto.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class OrganizationLoginRequest(
    @field:NotBlank
    @field:Length(min = 3, max = 1024)
    val email: String,

    @field:NotBlank
    @field:Length(min = 5, max = 1024)
    val password: String
)