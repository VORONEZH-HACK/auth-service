package space.teymurov.authenticationauthorizationservice.model.dto.request

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class GrantedRequest(
    @field:NotNull
    val userId: UUID
)
