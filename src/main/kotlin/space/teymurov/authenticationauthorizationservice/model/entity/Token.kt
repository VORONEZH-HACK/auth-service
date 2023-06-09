package space.teymurov.authenticationauthorizationservice.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tokens", schema = "fsp")
data class Token(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "type", nullable = false)
    val type: String,
)