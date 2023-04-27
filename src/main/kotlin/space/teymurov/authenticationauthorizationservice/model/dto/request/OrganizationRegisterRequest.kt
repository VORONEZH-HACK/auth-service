package space.teymurov.authenticationauthorizationservice.model.dto.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import space.teymurov.authenticationauthorizationservice.model.entity.Organization

data class OrganizationRegisterRequest(
    @field:NotBlank
    @field:Length(min = 3, max = 1024)
    val email: String,

    @field:NotBlank
    @field:Length(min = 2, max = 1024)
    val name: String,

    @field:NotBlank
    @field:Length(min = 5, max = 1024)
    val password: String
) {
    fun toOrganization(): Organization {
        return Organization(
            email = this.email,
            name = this.name,
            password = this.password
        )
    }
}