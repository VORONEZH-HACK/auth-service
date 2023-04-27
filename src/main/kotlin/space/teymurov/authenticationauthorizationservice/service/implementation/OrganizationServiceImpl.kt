package space.teymurov.authenticationauthorizationservice.service.implementation

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import space.teymurov.authenticationauthorizationservice.model.dto.exception.AbstractException
import space.teymurov.authenticationauthorizationservice.model.dto.request.OrganizationLoginRequest
import space.teymurov.authenticationauthorizationservice.model.dto.request.OrganizationRegisterRequest
import space.teymurov.authenticationauthorizationservice.model.dto.response.AbstractApiResponse
import space.teymurov.authenticationauthorizationservice.model.dto.response.RegisterLoginResponse
import space.teymurov.authenticationauthorizationservice.model.repository.OrganizationRepository
import space.teymurov.authenticationauthorizationservice.service.OrganizationService
import space.teymurov.authenticationauthorizationservice.util.common.GenerateToken

@Service
class OrganizationServiceImpl(
    private val organizationRepository: OrganizationRepository,
    private val passwordEncoder: PasswordEncoder,
    private val generateToken: GenerateToken
) : OrganizationService {
    override fun register(organizationRequest: OrganizationRegisterRequest): AbstractApiResponse<RegisterLoginResponse> {
        if (organizationRepository.findByEmail(organizationRequest.email) != null)
            throw AbstractException("Email address is already registered!")
        val encodePwdOrganization = organizationRequest.copy(
            password = passwordEncoder.encode(organizationRequest.password)
        )
        val org = encodePwdOrganization.toOrganization()
        val saved = organizationRepository.save(org)

        return generateToken.generateAndAttemptToken(
            email = organizationRequest.email,
            password = organizationRequest.password,
            saved.id,
            type = "ORGANIZATION"
        )
    }

    override fun login(organizationRequest: OrganizationLoginRequest): AbstractApiResponse<RegisterLoginResponse> {
        val org = organizationRepository.findByEmail(email = organizationRequest.email)
            ?: throw AbstractException("Email address not registered!")

        return generateToken.generateAndAttemptToken(email = org.email, password = org.password, org.id, type = "ORGANIZATION")
    }
}