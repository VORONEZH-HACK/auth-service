package space.teymurov.authenticationauthorizationservice.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import space.teymurov.authenticationauthorizationservice.model.entity.Token
import java.util.*

interface TokenRepository: JpaRepository<Token, UUID> {
}