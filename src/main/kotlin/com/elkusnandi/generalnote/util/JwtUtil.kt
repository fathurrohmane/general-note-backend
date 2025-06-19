package com.elkusnandi.generalnote.util

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

object JwtUtil {

    private const val EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
    private const val SECRET_KEY =
        "37e888dcb350bec8231c674a0f24e0c1ba3b5cdc39cf19f327655554649f263e91918fc3d78a1d2556e6d6fbc295e7ad7b84cf5c74aa9383cd352de5246b4264a99b2bbd20948b9a6b45f8fa03ab3f8cd3d7765f7beb7f9332e1ebe4bf0038662b17b39c9439c929fb403de3bde2178a05f772306c0468977e1ffcfbf690f24deaa7e6a759ffd511035d61e86070c09323507f165cd1ca7aa93f6ab1200598489a767a77d07d06099cae21618d155c5613de2c3e4971772a5804e198b9a30fa51e7cb18c2ccc98bffc08ea399a6da29bf503510a7b0b290d06a36820f8680e545f3fa8743edc36e09e9a4b75509f65013099a1053aff484ff33a70e56d0ef105"

    fun generateToken(userId: String): String =
        Jwts
            .builder()
            .setSubject(userId)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.toByteArray()), SignatureAlgorithm.HS256)
            .compact()

    fun getAllClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY.toByteArray())
            .build()
            .parseClaimsJws(token).body

    fun isValid(token: String, userId: String): Boolean {
        try {
            return getAllClaims(token).subject == userId && getAllClaims(token).expiration.after(Date())
        } catch (_: SignatureException) {

        } catch (_: MalformedJwtException) {

        } catch (_: ExpiredJwtException) {

        } catch (_: UnsupportedJwtException) {

        } catch (_: IllegalArgumentException) {

        }
        return false
    }

}

@Component
class Encryption {

    @Bean
    fun byCrypt() = BCryptPasswordEncoder()
}