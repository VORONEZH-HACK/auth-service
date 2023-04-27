package space.teymurov.authenticationauthorizationservice.model.dto.response

import org.springframework.http.HttpStatus

data class AbstractApiResponse<T>(
    val code: Int = HttpStatus.OK.value(),
    val message: String = HttpStatus.OK.name,
    val data: T? = null
)
