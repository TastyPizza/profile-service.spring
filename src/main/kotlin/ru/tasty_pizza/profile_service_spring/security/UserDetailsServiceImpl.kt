package ru.tasty_pizza.profile_service_spring.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.tasty_pizza.profile_service_spring.entity.Client
import ru.tasty_pizza.profile_service_spring.repo.ClientRepository

@Service
class UserDetailsServiceImpl (
    private val clientRepository: ClientRepository
) : UserDetailsService {
    
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: Client = clientRepository
            .findByEmail(username)
            .orElseThrow { UsernameNotFoundException("Client '$username' not found.") }
        
        return User(
            user.email,
            user.verificationCode, 
            setOf()
        )
    }
}