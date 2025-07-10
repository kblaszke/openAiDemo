package pl.blaszak.ai.demoapiclient.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import pl.blaszak.ai.demoapiclient.MessageRepository
import pl.blaszak.ai.demoapiclient.service.OpenAiService
import pl.blaszak.ai.demoapiclient.service.ChatService
import pl.blaszak.ai.demoapiclient.service.ChromaDbService

@Configuration
@EnableConfigurationProperties(AppSettingsProperties::class)
@EnableScheduling
class Configuration(private val appSettings: AppSettingsProperties) {

    @Bean
    fun apiService(appSettings: AppSettingsProperties) = OpenAiService(appSettings.spring.ai.openai.apiKey)

    @Bean
    fun chatService(
        messageRepository: MessageRepository,
        openAiService: OpenAiService,
        chromaDbService: ChromaDbService
    ) = ChatService(
        messageRepository,
        openAiService,
        chromaDbService,
        appSettings.db.chroma.collection
    )

    @Bean
    fun chromaDbService() = ChromaDbService(appSettings.db.chroma.url)
}