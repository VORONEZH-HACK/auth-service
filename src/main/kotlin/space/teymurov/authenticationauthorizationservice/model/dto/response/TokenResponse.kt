package space.teymurov.authenticationauthorizationservice.model.dto.response

import org.springframework.http.HttpStatus
import java.util.*

class TokenResponse(
    val code: Int = HttpStatus.OK.value(),
    val token: UUID? = null,
    val type: String? = null
)