package space.teymurov.authenticationauthorizationservice.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import space.teymurov.authenticationauthorizationservice.model.dto.response.UserAuthResponse
import space.teymurov.authenticationauthorizationservice.util.Constant.JWT_TOKEN_EXPIRED
import space.teymurov.authenticationauthorizationservice.util.EnvironmentVariables
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    appProperties: EnvironmentVariables
) {
    private val base64EncodeKey: ByteArray = Base64.getEncoder().encode(appProperties.secretKey.toByteArray())
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(base64EncodeKey)

    fun generateAccessToken(userAuth: UserAuthResponse): String {
        return Jwts.builder()
            .claim("user-uuid", userAuth.userID)
            .claim("token-uuid", userAuth.tokenID)
            .claim("type", userAuth.type)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateAccessToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        }catch (e: JwtException){
            false
        }
    }

    fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}