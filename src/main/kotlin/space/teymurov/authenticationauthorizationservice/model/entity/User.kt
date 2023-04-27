package space.teymurov.authenticationauthorizationservice.model.entity

import jakarta.persistence.*
import space.teymurov.authenticationauthorizationservice.model.dto.response.UserAuthResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.UserResponse
import java.util.*

@Entity
@Table(name = "users", schema = "fsp")
data class User(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "surname", nullable = false)
    val surname: String,

    @Column(name = "patronymic", nullable = true)
    val patronymic: String?,

    @Column(name = "edu", nullable = false)
    val edu: String,

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "password")
    val password: String,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "users_x_roles", schema = "fsp",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: MutableList<Role> = mutableListOf()
) {
    fun toUserResponse(): UserResponse {
        return UserResponse(
            id = this.id,
            name = this.name,
            surname = this.surname,
            patronymic = this.patronymic,
            edu = this.edu,
            email = this.email,
            roles = roles.map { it.name }
        )
    }

    fun toUserAuth(): UserAuthResponse {
        return UserAuthResponse(
            email = this.email,
            password = this.password,
            roles = this.roles.map { it.name },
            userID = this.id,
            type = "USER"
        )
    }
}