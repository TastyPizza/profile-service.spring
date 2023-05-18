package ru.tasty_pizza.profile_service_spring.security

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


class JwtTokenProvider (
    private val privateKey: PrivateKey,
    val publicKey: PublicKey 
) {

    private val log = LoggerFactory.getLogger(this.javaClass)
    private fun generateToken(
        login: String,
        claims: MutableMap<String, Any>? = null,
        expirationDate: Date = Date.from(Instant.now().plus(7, ChronoUnit.DAYS))
    ): String {
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(login)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact()
    }
    
    fun generateAccessToken(login: String): String {
        val expirationDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES))
        val claims: MutableMap<String, Any> = HashMap()
        claims["tokenType"] = "access"
        claims["role"] = "ROLE_VERIFIED_CLIENT"
        
        return "Bearer " + generateToken(login, claims, expirationDate)
    }

    fun generateRefreshToken(login: String): String {
        val expirationDate = Date.from(Instant.now().plus(12, ChronoUnit.HOURS))
        val claims: MutableMap<String, Any> = HashMap()
        claims["tokenType"] = "refresh"
        claims["role"] = "ROLE_VERIFIED_CLIENT"

        return "Bearer " + generateToken(login, claims, expirationDate)
    }

    fun generateReadyToGetVerifiedToken(login: String): String {
        val expirationDate = Date.from(Instant.now().plus(10, ChronoUnit.MINUTES))
        val claims: MutableMap<String, Any> = HashMap()
        claims["tokenType"] = "verify"
        claims["role"] = "ROLE_UNVERIFIED_CLIENT"

        return "Bearer " + generateToken(login, claims, expirationDate)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            log.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            log.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            log.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            log.error("JWT claims string is empty.")
        }
        return false
    }
    
    fun validateTokenType(token: String, type: String): Boolean {
        if (!validateToken(token)) return false

        return Jwts
            .parser()
            .setSigningKey(publicKey)
            .parseClaimsJwt(token)
            .body["tokenType"]?.equals(type) ?: false
    }

    fun getUserLoginFromToken(token: String): String {
        
        return Jwts
            .parser()
            .setSigningKey(publicKey)
            .parseClaimsJws(token)
            .body.subject
    }

    fun getTokenType(token: String): String {
        
        return Jwts
            .parser()
            .setSigningKey(publicKey)
            .parseClaimsJws(token)
            .body["tokenType"] as String
    }

    fun getRole(token: String): String {

        return Jwts
            .parser()
            .setSigningKey(publicKey)
            .parseClaimsJws(token)
            .body["role"] as String
    }
}