package pl.blaszak.ai.demoapiclient.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "myapp.settings")
data class SecretProperties(val apiKey: String, val cleanDataDelay: Long)
