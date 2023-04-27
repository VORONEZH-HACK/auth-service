package space.teymurov.authenticationauthorizationservice.security.jwt

import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.web.filter.OncePerRequestFilter
import space.teymurov.authenticationauthorizationservice.model.dto.response.UserAuthResponse
import java.util.*

@Component
class JwtTokenFilter(
    val jwtTokenUtil: JwtTokenUtil
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if(!hasAuthorizationBearer(request)){
            filterChain.doFilter(request, response)
            return
        }

        val token: String = getAccessToken(request)

        if (!jwtTokenUtil.validateAccessToken(token = token)){
            filterChain.doFilter(request, response)
            return
        }

        setAuthenticationContext(token, request)
        filterChain.doFilter(request, response)
    }

    private fun setAuthenticationContext(token: String, request: HttpServletRequest) {
        val userDetails: UserDetails = getUserDetails(token)

        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun getUserDetails(token: String): UserDetails {
        val claims = jwtTokenUtil.parseClaims(token = token)
        val subject = claims[Claims.SUBJECT] as String
        val userID = UUID.fromString(claims["user-uuid"] as String)
        val tokenID = UUID.fromString(claims["token-uuid"] as String)
        val type = claims["type"] as String

        val textRoles = claims["roles"] as String
        val roles = textRoles.split(",")

        return UserAuthResponse(email = subject, userID = userID,  roles = roles, tokenID = tokenID, type = type)
    }

    private fun getAccessToken(request: HttpServletRequest): String {
        val header = request.getHeader("Authorization")
        return header.split(" ")[1].trim()
    }

    private fun hasAuthorizationBearer(request: HttpServletRequest): Boolean {
        val header: String? = request.getHeader("Authorization")
        return !(header == null || ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"))
    }
}