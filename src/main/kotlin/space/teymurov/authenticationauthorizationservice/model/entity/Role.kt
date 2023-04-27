package space.teymurov.authenticationauthorizationservice.model.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "roles", schema = "fsp")
data class Role(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @ManyToMany(mappedBy = "roles")
    val user: List<User> = mutableListOf()
)
