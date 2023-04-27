package space.teymurov.authenticationauthorizationservice.model.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import space.teymurov.authenticationauthorizationservice.model.entity.User

data class UserRegisterRequest(
    @field:NotBlank
    @field:Length(min = 2, max = 50)
    val name: String,

    @field:NotBlank
    @field:Length(min = 2, max = 100)
    val surname: String,

    @field:NotBlank
    @field:Length(min = 2, max = 150)
    val patronymic: String? = null,

    @field:NotBlank
    @field:Length(min = 2)
    val edu: String,

    @field:NotBlank
    @field:Email
    @field:Length(min = 3, max = 1000)
    val email: String,

    @field:NotBlank
    @field:Length(min = 5, max = 1000)
    val password: String
){
    fun toUser(): User {
        return User(
            name = this.name,
            surname = this.surname,
            patronymic = this.patronymic,
            edu = this.edu,
            email = this.email,
            password = this.password
        )
    }
}
