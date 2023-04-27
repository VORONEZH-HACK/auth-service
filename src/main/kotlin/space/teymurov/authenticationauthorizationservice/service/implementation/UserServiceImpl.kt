package space.teymurov.authenticationauthorizationservice.service.implementation

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import space.teymurov.authenticationauthorizationservice.model.dto.exception.AbstractException
import space.teymurov.authenticationauthorizationservice.model.dto.request.GrantedRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.UserLoginRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.UserRegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.response.*
import space.teymurov.authenticationauthorizationservice.model.entity.Token
import space.teymurov.authenticationauthorizationservice.model.repository.RoleRepository
import space.teymurov.authenticationauthorizationservice.model.repository.TokenRepository
import space.teymurov.authenticationauthorizationservice.model.repository.UserRepository
import space.teymurov.authenticationauthorizationservice.security.jwt.JwtTokenUtil
import space.teymurov.authenticationauthorizationservice.service.UserService
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_ADMIN
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_USER
import space.teymurov.authenticationauthorizationservice.util.common.GenerateToken
import java.lang.Exception
import java.util.*

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val tokenRepository: TokenRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtTokenUtil: JwtTokenUtil,
    val generateToken: GenerateToken
) : UserService {

    override fun getAllUser(): AbstractApiResponse<List<UserResponse>> {
        return AbstractApiResponse(data = userRepository.findAll().map { it.toUserResponse() })
    }

    override fun register(registerRequest: UserRegisterRequest): AbstractApiResponse<RegisterLoginResponse> {
        if (userRepository.findByEmail(email = registerRequest.email) != null) {
            throw AbstractException("Email address is already registered!")
        }

        val encodePwdUser = registerRequest.copy(
            password = passwordEncoder.encode(registerRequest.password)
        )
        val user = encodePwdUser.toUser()
        user.roles.add(roleRepository.findByName(name = ROLE_USER).get())
        val created = userRepository.save(user)

        return generateToken.generateAndAttemptToken(
            email = registerRequest.email,
            password = registerRequest.password,
            created.id,
            type = "USER"
        )
    }

    override fun login(loginRequest: UserLoginRequest): AbstractApiResponse<RegisterLoginResponse> {
        val user = userRepository.findByEmail(email = loginRequest.email)
            ?: throw AbstractException("Email address not registered!")

        return generateToken.generateAndAttemptToken(
            email = loginRequest.email,
            password = loginRequest.password,
            user = user.id,
            type = "USER"
        )
    }

    override fun logout(token: String): AbstractApiResponse<String> {
        return try {
            tokenRepository.deleteById(UUID.fromString(jwtTokenUtil.parseClaims(token)["token-uuid"] as String))
            AbstractApiResponse(200, "Successfully deleted!")
        } catch (e: Exception) {
            AbstractApiResponse(400, "Bad request!")
        }
    }

    override fun token(token: String): TokenResponse {
        return try {
            val claims = jwtTokenUtil.parseClaims(token)
            val tokenID =
                tokenRepository.findById(UUID.fromString(claims["token-uuid"] as String))
            if (!jwtTokenUtil.validateAccessToken(token))
                throw AbstractException("token not valid!")
            else if (tokenID.isPresent)
                TokenResponse(200, tokenID.get().id, claims["type"] as String)
            else
                TokenResponse(400)
        } catch (e: Exception) {
            TokenResponse(400)
        }
    }

    override fun profile(email: String): AbstractApiResponse<UserResponse> {
        if (userRepository.findByEmail(email = email) == null) {
            throw AbstractException("Access token is not valid!")
        }

        return AbstractApiResponse(data = userRepository.findByEmail(email = email)!!.toUserResponse())
    }

    override fun grantAsAdmin(grantedRequest: GrantedRequest): AbstractApiResponse<Nothing> {
        findExistingUserById(userId = grantedRequest.userId)

        val user = userRepository.findById(grantedRequest.userId).get()
        val roleAdmin = roleRepository.findByName(ROLE_ADMIN).get()

        if (user.roles.contains(roleAdmin)) {
            throw AbstractException("User has already as administrators")
        }

        user.roles.remove(roleRepository.findByName(ROLE_USER).get())
        user.roles.add(roleAdmin)
        userRepository.save(user)
        return AbstractApiResponse()
    }

    override fun unGrantAsAdmin(grantedRequest: GrantedRequest): AbstractApiResponse<Nothing> {
        findExistingUserById(userId = grantedRequest.userId)

        val user = userRepository.findById(grantedRequest.userId).get()
        val roleAdmin = roleRepository.findByName(ROLE_ADMIN).get()

        if (!user.roles.contains(roleAdmin)) {
            throw AbstractException("User not as administrators")
        }

        user.roles.remove(roleAdmin)
        user.roles.add(roleRepository.findByName(ROLE_USER).get())
        userRepository.save(user)
        return AbstractApiResponse()
    }

    private fun findExistingUserById(userId: UUID) {
        if (!userRepository.existsById(userId)) {
            throw AbstractException("User id not found!")
        }
    }
}