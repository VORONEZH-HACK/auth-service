package space.teymurov.authenticationauthorizationservice.controller

import jakarta.annotation.security.RolesAllowed
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import space.teymurov.authenticationauthorizationservice.model.dto.request.GrantedRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.LoginRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.RegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.response.*
import space.teymurov.authenticationauthorizationservice.service.UserService
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_ADMIN
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_USER
import java.util.*

@RestController
@RequestMapping("/api/v1/")
class UserController(
    val userService: UserService
) {
    @PostMapping(
        value = ["register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun register(@RequestBody registerRequest: RegisterRequest): AbstractApiResponse<RegisterLoginResponse>{
        return userService.register(registerRequest = registerRequest)
    }

    @PostMapping(
        value = ["login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody loginRequest: LoginRequest): AbstractApiResponse<RegisterLoginResponse>{
        return userService.login(loginRequest = loginRequest)
    }

    @PostMapping(
        value = ["logout"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun logout(@RequestHeader("Access-Token") accessToken: String): AbstractApiResponse<String> {
        return userService.logout(accessToken)
    }

    @GetMapping(
        value = ["token"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun token(@RequestHeader("Access-Token") accessToken: String): TokenResponse {
        return userService.token(accessToken)
    }

    @GetMapping(
        value = ["me"],
        produces = ["application/json"]
    )
    @RolesAllowed(ROLE_USER, ROLE_ADMIN)
    fun profile(authentication: Authentication): AbstractApiResponse<UserResponse> {
        val userAuth = authentication.principal as UserAuthResponse
        return userService.profile(email = userAuth.email)
    }

    @GetMapping(
        value = ["users"],
        produces = ["application/json"]
    )
    @RolesAllowed(ROLE_ADMIN)
    fun getAllUser(): AbstractApiResponse<List<UserResponse>> {
        return userService.getAllUser()
    }



    @PostMapping(
        value = ["grant"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @RolesAllowed(ROLE_ADMIN)
    fun grantAsAdmin(@RequestBody grantedRequest: GrantedRequest): AbstractApiResponse<Nothing> {
        return userService.grantAsAdmin(grantedRequest = grantedRequest)
    }

    @PostMapping(
        value = ["ungrant"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @RolesAllowed(ROLE_ADMIN)
    fun unGrantAsAdmin(@RequestBody grantedRequest: GrantedRequest): AbstractApiResponse<Nothing> {
        return userService.unGrantAsAdmin(grantedRequest = grantedRequest)
    }
}