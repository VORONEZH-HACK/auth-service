package space.teymurov.authenticationauthorizationservice.service

import space.teymurov.authenticationauthorizationservice.model.dto.request.GrantedRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.UserLoginRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.UserRegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.response.AbstractApiResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.RegisterLoginResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.TokenResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.UserResponse

interface UserService {
    fun register(registerRequest: UserRegisterRequest): AbstractApiResponse<RegisterLoginResponse>
    fun login(loginRequest: UserLoginRequest): AbstractApiResponse<RegisterLoginResponse>
    fun logout(token: String): AbstractApiResponse<String>
    fun token(token: String): TokenResponse
    fun profile(email: String): AbstractApiResponse<UserResponse>
    fun getAllUser(): AbstractApiResponse<List<UserResponse>>
    fun grantAsAdmin(grantedRequest: GrantedRequest): AbstractApiResponse<Nothing>
    fun unGrantAsAdmin(grantedRequest: GrantedRequest): AbstractApiResponse<Nothing>
}