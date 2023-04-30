package ru.tasty_pizza.profile_service_spring.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.tasty_pizza.profile_service_spring.entity.Client
import java.util.*

@Repository
interface ClientRepository : JpaRepository<Client?, Int?> {
    
    @Query("select c from Client c where c.email = ?1")
    fun findByEmail(email: String): Optional<Client>
}