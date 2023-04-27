package space.teymurov.authenticationauthorizationservice.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import space.teymurov.authenticationauthorizationservice.model.entity.Organization
import java.util.*

interface OrganizationRepository: JpaRepository<Organization, UUID> {
    fun findByEmail(email: String): Organization?
}