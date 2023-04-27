package space.teymurov.authenticationauthorizationservice.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "organizations", schema = "fsp")
data class Organization(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "password", nullable = false)
    val password: String
)