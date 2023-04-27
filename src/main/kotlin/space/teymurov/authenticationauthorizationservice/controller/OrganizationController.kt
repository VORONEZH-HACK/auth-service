package space.teymurov.authenticationauthorizationservice.controller

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
    fun register(@RequestBody registerRequest: OrganizationRegisterRequest): AbstractApiResponse<RegisterLoginResponse> {
        return organizationService.register(organizationRequest = registerRequest)
    }
    @PatchMapping(
        value = ["login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody loginRequest: OrganizationLoginRequest): AbstractApiResponse<RegisterLoginResponse> {
        return organizationService.login(organizationRequest = loginRequest)
    }
}