package space.teymurov.authenticationauthorizationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthenticationAuthorizationServiceApplication

fun main(args: Array<String>) {
	runApplication<AuthenticationAuthorizationServiceApplication>(*args)
}
