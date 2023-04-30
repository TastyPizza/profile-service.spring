package ru.tasty_pizza.profile_service_spring.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.tasty_pizza.profile_service_spring.entity.Client
import ru.tasty_pizza.profile_service_spring.service.ClientService

@RestController
@RequestMapping("profile")
class ProfilesController (
    private val clientService: ClientService
) {
    @GetMapping
    fun getProfile(): ResponseEntity<Client> = clientService.getProfile()
}