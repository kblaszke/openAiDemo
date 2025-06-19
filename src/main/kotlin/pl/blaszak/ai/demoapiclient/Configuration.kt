package pl.blaszak.ai.demoapiclient

import org.springframework.context.annotation.Configuration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@Configuration
@EnableConfigurationProperties(SecretProperties::class)
class Configuration(private val secretProperties: SecretProperties) {

    @Bean
    fun apiService(secretProperties: SecretProperties) = ApiService(secretProperties.apiKey)

    @Bean
    fun promptGenerator() = PromptGenerator()

}