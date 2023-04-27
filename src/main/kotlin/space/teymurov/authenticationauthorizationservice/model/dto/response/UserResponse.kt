package space.teymurov.authenticationauthorizationservice.model.dto.response

import java.util.*

data class UserResponse(
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val edu: String,
    val email: String,
    val roles: List<String>?
)