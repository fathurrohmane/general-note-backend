package com.elkusnandi.generalnote.filter

import com.elkusnandi.generalnote.service.UserService
import com.elkusnandi.generalnote.util.JwtUtil
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
            val userName = JwtUtil.getAllClaims(token).subject

            if (userName != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userService.loadUserByUsername(userName)
                if (JwtUtil.isValid(token, userDetails.username)) {
                    val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = auth
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid jwt token")
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}