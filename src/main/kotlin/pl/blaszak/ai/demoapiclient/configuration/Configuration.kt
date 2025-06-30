package pl.blaszak.ai.demoapiclient.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import pl.blaszak.ai.demoapiclient.MessageRepository
import pl.blaszak.ai.demoapiclient.service.OpenAiService
import pl.blaszak.ai.demoapiclient.service.ChatService

@Configuration
@EnableConfigurationProperties(SecretProperties::class)
@EnableScheduling
class Configuration(private val secretProperties: SecretProperties) {

    @Bean
    fun apiService(secretProperties: SecretProperties) = OpenAiService(secretProperties.apiKey)

    @Bean
    fun chatService(messageRepository: MessageRepository, openAiService: OpenAiService) = ChatService(messageRepository, openAiService)
}