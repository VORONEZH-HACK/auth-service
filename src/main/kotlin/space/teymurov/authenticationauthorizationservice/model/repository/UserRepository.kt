package space.teymurov.authenticationauthorizationservice.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import space.teymurov.authenticationauthorizationservice.model.entity.User
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}