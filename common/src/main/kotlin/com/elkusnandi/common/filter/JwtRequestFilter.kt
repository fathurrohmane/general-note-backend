package com.elkusnandi.common.filter

import com.elkusnandi.common.service.UserService
import com.elkusnandi.common.util.JwtUtil
import io.jsonwebtoken.Jwt
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtRequestFilter(
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")?.substringAfter("Bearer ")

        if (token?.isNotBlank() == true) {
            if (SecurityContextHolder.getContext().authentication == null) {
                val claims = JwtUtil.checkAndGetClaims(token)

                if (claims != null) {
                    val userDetails = userService.loadUserByUsername(claims.subject)
                    val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = auth
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired jwt token")
                    return
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}