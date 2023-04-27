package space.teymurov.authenticationauthorizationservice.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import space.teymurov.authenticationauthorizationservice.model.repository.UserRepository
import space.teymurov.authenticationauthorizationservice.security.jwt.JwtTokenFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = false, securedEnabled = false, jsr250Enabled = true)
class ApplicationSecurityConfig(
    val userRepository: UserRepository,
    val jwtTokenFilter: JwtTokenFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authConfig: AuthenticationConfiguration,
    ): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    fun configure(
        http: HttpSecurity
    ): SecurityFilterChain{
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(STATELESS)
        http.authorizeHttpRequests().requestMatchers("/api/v1/register", "/api/v1/login").permitAll()
        http.authorizeHttpRequests().anyRequest().permitAll()
        http.exceptionHandling().authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService {
            val user = userRepository.findByEmail(email = it)

            return@UserDetailsService user?.toUserAuth() ?: throw UsernameNotFoundException("User email $it not found!")
        }
    }
}