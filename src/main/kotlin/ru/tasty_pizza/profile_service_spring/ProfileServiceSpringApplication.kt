package ru.tasty_pizza.profile_service_spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ProfileServiceSpringApplication 

fun main(args: Array<String>) {
    runApplication<ProfileServiceSpringApplication>(*args)
}