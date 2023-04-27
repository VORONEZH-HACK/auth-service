package space.teymurov.authenticationauthorizationservice.model.dto.response

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserAuthResponse(
    val email: String,
    var userID: UUID,
    var tokenID: UUID? = null,
    private val password: String? = "",
    val roles: List<String> = listOf()
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<SimpleGrantedAuthority>()
        this.roles.forEach {
            authorities.add(SimpleGrantedAuthority(it))
        }
        return authorities
    }

    override fun getPassword(): String {
        return this.password.orEmpty()
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}