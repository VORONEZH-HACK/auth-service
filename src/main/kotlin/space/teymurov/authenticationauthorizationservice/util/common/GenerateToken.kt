package space.teymurov.authenticationauthorizationservice.util.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import space.teymurov.authenticationauthorizationservice.model.dto.exception.AbstractException
import space.teymurov.authenticationauthorizationservice.model.dto.response.AbstractApiResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.RegisterLoginResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.UserAuthResponse
import space.teymurov.authenticationauthorizationservice.model.entity.Token
import space.teymurov.authenticationauthorizationservice.model.repository.TokenRepository
import space.teymurov.authenticationauthorizationservice.security.jwt.JwtTokenUtil
import java.util.*

@Component
class GenerateToken(
    val tokenRepository: TokenRepository,
    val authenticationManager: AuthenticationManager,
    val jwtTokenUtil: JwtTokenUtil
){

    @Throws(AbstractException::class)
    fun generateAndAttemptToken(email: String, password: String, user: UUID, type: String): AbstractApiResponse<RegisterLoginResponse> {
        try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )

            val userAuth: UserAuthResponse = authentication.principal as UserAuthResponse
            userAuth.userID = user

            val id: UUID = UUID.randomUUID()
            val token = Token(id, type)
            tokenRepository.save(token)
            userAuth.tokenID = id
            userAuth.type = type

            val accessToken = jwtTokenUtil.generateAccessToken(userAuth = userAuth)

            return AbstractApiResponse(data = RegisterLoginResponse(accessToken = accessToken))

        }catch (e: BadCredentialsException){
            throw AbstractException("Email and password is not correctly!")
        }
    }
}