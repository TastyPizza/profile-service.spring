package ru.tasty_pizza.profile_service_spring.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import ru.tasty_pizza.profile_service_spring.security.JwtTokenProvider


@Component
class StartupRunner(
    @Value("\${app.exchange}")
    private val EXCHANGE: String,

    @Value("\${app.routing-key}")
    private val ROUTING_KEY: String
) : ApplicationRunner {
    private val log: Logger = LoggerFactory.getLogger(StartupRunner::class.java)
    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired
    private lateinit var amqpTemplate: AmqpTemplate
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    //		Will try to get public key from profile service on application startup
    override fun run(args: ApplicationArguments?) {
        try {
            val publicKeyJson = objectMapper.writeValueAsString(jwtTokenProvider.publicKey.encoded)
            amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY,publicKeyJson)
            log.info("Successfully send a public key to 'public-key' channel")
        } catch (ex : Exception){
            log.error(ex.message)
        }
    }
}
