package space.teymurov.authenticationauthorizationservice.service

import space.teymurov.authenticationauthorizationservice.model.dto.request.OrganizationLoginRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.OrganizationRegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.response.AbstractApiResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.RegisterLoginResponse

interface OrganizationService {
    fun register(organizationRequest: OrganizationRegisterRequest): AbstractApiResponse<RegisterLoginResponse>
    fun login(organizationRequest: OrganizationLoginRequest): AbstractApiResponse<RegisterLoginResponse>
}