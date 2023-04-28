package space.teymurov.authenticationauthorizationservice.controller

import jakarta.annotation.security.RolesAllowed
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import space.teymurov.authenticationauthorizationservice.model.dto.request.GrantedRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.UserLoginRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.UserRegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.response.*
import space.teymurov.authenticationauthorizationservice.service.UserService
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_ADMIN
import space.teymurov.authenticationauthorizationservice.util.Constant.ROLE_USER
import java.util.*
import javax.swing.text.StyledEditorKit.BoldAction

@RestController
@RequestMapping("/api/v1/")
class UserController(
    val userService: UserService
) {
    @PostMapping(
        value = ["user/register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun register(@RequestBody registerRequest: UserRegisterRequest): ResponseEntity<RegisterLoginResponse> {
        val response = userService.register(registerRequest)
        return ResponseEntity<RegisterLoginResponse>(response.data, HttpStatusCode.valueOf(response.code))
    }

    @PatchMapping(
        value = ["user/login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody loginRequest: UserLoginRequest): ResponseEntity<RegisterLoginResponse> {
        val response = userService.login(loginRequest = loginRequest)
        return ResponseEntity<RegisterLoginResponse>(response.data, HttpStatusCode.valueOf(response.code))
    }

    @PatchMapping(
        value = ["logout"],
        produces = ["application/json"]
    )
    fun logout(@RequestHeader("Access-Token") accessToken: String): ResponseEntity<String> {
        val response = userService.logout(accessToken)
        return ResponseEntity<String>(response.data ?: response.message, HttpStatusCode.valueOf(response.code))
    }

    @GetMapping(
        value = ["token"],
        produces = ["application/json"]
    )
    fun token(@RequestHeader("Access-Token") accessToken: String): ResponseEntity<TokenResponse> {
        val response =  userService.token(accessToken)
        return ResponseEntity<TokenResponse>(response, HttpStatusCode.valueOf(200))
    }

    @GetMapping(
        value = ["user/me"],
        produces = ["application/json"]
    )
    @RolesAllowed(ROLE_USER, ROLE_ADMIN)
    fun profile(authentication: Authentication): ResponseEntity<UserResponse> {
        val userAuth = authentication.principal as UserAuthResponse
        val response = userService.profile(email = userAuth.email)
        return ResponseEntity<UserResponse>(response.data, HttpStatusCode.valueOf(response.code))
    }

    @PostMapping(
        value = ["user/grant"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @RolesAllowed(ROLE_ADMIN)
    fun grantAsAdmin(@RequestBody grantedRequest: GrantedRequest): ResponseEntity<Nothing> {
        val response = userService.grantAsAdmin(grantedRequest = grantedRequest)
        return ResponseEntity<Nothing>(HttpStatusCode.valueOf(200))
    }

    @PostMapping(
        value = ["user/ungrant"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    @RolesAllowed(ROLE_ADMIN)
    fun unGrantAsAdmin(@RequestBody grantedRequest: GrantedRequest): ResponseEntity<Nothing> {
        val response = userService.unGrantAsAdmin(grantedRequest = grantedRequest)
        return ResponseEntity<Nothing>(HttpStatusCode.valueOf(200))
    }
}