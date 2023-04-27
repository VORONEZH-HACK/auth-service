package space.teymurov.authenticationauthorizationservice.model.dto.response

import java.util.*

data class OrganizationResponse(
    val id: UUID,
    val name: String,
    val email: String,
    val password: String
)