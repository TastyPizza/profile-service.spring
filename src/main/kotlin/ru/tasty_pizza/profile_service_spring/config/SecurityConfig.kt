package ru.tasty_pizza.profile_service_spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.tasty_pizza.profile_service_spring.security.JwtAuthenticationFilter
import ru.tasty_pizza.profile_service_spring.security.JwtAuthenticationPoint
import ru.tasty_pizza.profile_service_spring.security.JwtTokenProvider
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val unauthorizedHandler: JwtAuthenticationPoint
) {

    @Bean
    fun jwtTokenProvider(): JwtTokenProvider {
        val keyGenerator = KeyPairGenerator.getInstance("RSA")
        keyGenerator.initialize(1024)

        val kp = keyGenerator.genKeyPair()

        return JwtTokenProvider(kp.private as PrivateKey, kp.public as PublicKey)
    }

    @Bean
    fun jwtAuthenticationFilter(jwtTokenProvider: JwtTokenProvider): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtTokenProvider)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager? {
        return authConfig.authenticationManager
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        val source = UrlBasedCorsConfigurationSource()
        
        configuration.allowedOrigins = listOf("localhost")
        source.registerCorsConfiguration("/**", configuration)
        
        return source
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .addFilterAt(jwtAuthenticationFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter::class.java)
            .cors()
            .and()
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler) //если пользователь не зарегестрирован,то он обрабатывается тут
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/auth/sign-in", "/auth/sign-up", "/public-key")
                .permitAll()

                /*.requestMatchers("/auth/refresh-token")
                .hasAuthority("CAN_REFRESH")*/
                
                .requestMatchers("/auth/verification")
                .hasAuthority("ROLE_UNVERIFIED_CLIENT")
                
                .requestMatchers("/profile/**")
                .hasAuthority("ROLE_VERIFIED_CLIENT")
                
                .requestMatchers("*")
                .permitAll()
            .and()
            .build()
    }
}