package space.teymurov.authenticationauthorizationservice.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import space.teymurov.authenticationauthorizationservice.model.entity.Role
import java.util.*

interface RoleRepository: JpaRepository<Role, UUID> {
    fun findByName(name: String): Optional<Role>
}