package pl.blaszak.ai.demoapiclient.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "")
data class AppSettingsProperties @ConstructorBinding constructor(
    val spring: Spring,
    val db: Db
) {
    data class Spring(val ai: Ai) {
        data class Ai(val openai: OpenAi) {
            data class OpenAi(
                val apiKey: String,
                val chat: Chat
            ) {
                data class Chat(val options: Options) {
                    data class Options(
                        val model: String,
                        val temperature: Double,
                        val maxTokens: Int
                    )
                }
            }
        }
    }

    data class Db(
        val chroma: Chroma
    ) {
        data class Chroma(
            val url: String,
            val collection: String,
            val cleanDataDelay: Long
        )
    }
}