package space.teymurov.authenticationauthorizationservice.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EnvironmentVariables {
    @Value("\${app.jwt.secret}")
    lateinit var secretKey: String
}