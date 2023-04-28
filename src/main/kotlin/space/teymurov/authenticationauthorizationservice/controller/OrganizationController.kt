package space.teymurov.authenticationauthorizationservice.controller

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import space.teymurov.authenticationauthorizationservice.model.dto.request.OrganizationLoginRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.OrganizationRegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.UserRegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.response.AbstractApiResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.RegisterLoginResponse
import space.teymurov.authenticationauthorizationservice.service.OrganizationService

@RestController
@RequestMapping("/api/v1/organization/")
class OrganizationController(
    val organizationService: OrganizationService
) {
    @PostMapping(
        value = ["register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun register(@RequestBody registerRequest: OrganizationRegisterRequest): ResponseEntity<RegisterLoginResponse> {
        val response = organizationService.register(organizationRequest = registerRequest)
        return ResponseEntity<RegisterLoginResponse>(response.data, HttpStatusCode.valueOf(response.code))
    }
    @PatchMapping(
        value = ["login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody loginRequest: OrganizationLoginRequest): ResponseEntity<RegisterLoginResponse> {
        val response = organizationService.login(organizationRequest = loginRequest)
        return ResponseEntity<RegisterLoginResponse>(response.data, HttpStatusCode.valueOf(response.code))
    }
}