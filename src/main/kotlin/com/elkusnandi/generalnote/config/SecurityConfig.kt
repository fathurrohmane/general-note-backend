package com.elkusnandi.generalnote.config

import com.elkusnandi.generalnote.exception.CustomAuthenticationEntryPoint
import com.elkusnandi.generalnote.filter.JwtRequestFilter
import com.elkusnandi.generalnote.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userService: UserService,
    private val jwtRequestFilter: JwtRequestFilter,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }  // disable CSRF for REST APIs
            .sessionManagement { sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/public/api/**",
                        "/swagger-ui/**",
                        "/v3/**",
                        "/error",
                        "/s/**"
                    ) // need authentication selected end point
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .userDetailsService(userService)
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { ex ->
                ex.authenticationEntryPoint(customAuthenticationEntryPoint)
            }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf(
            "http://localhost:5173",
            "https://www.malubertanya.com",
            "https://api.malubertanya.com",
            "https://malubertanya.com",
            "https://api-dev.malubertanya.com"
        )
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}

@Component
class Encryption {

    @Bean
    fun byCrypt(): PasswordEncoder = BCryptPasswordEncoder()
}
