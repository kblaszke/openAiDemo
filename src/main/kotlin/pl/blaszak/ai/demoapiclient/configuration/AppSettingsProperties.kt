package pl.blaszak.ai.demoapiclient.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "")
data class AppSettingsProperties @ConstructorBinding constructor(
    val spring: Spring,
    val ai: Ai,
    val db: Db
) {
    data class Spring(val ai: AI) {
        data class AI(val openai: OpenAi) {
            data class OpenAi(
                val apiKey: String,
                val chat: Chat
            ) {
                data class Chat(val options: Options) {
                    data class Options(val model: String, val temperature: Double)
                }
            }
        }
    }

    data class Ai(
        val openai: OpenAI
    ) {
        data class OpenAI(
            val embedding: Embedding
        ) {


            data class Embedding(
                val model: String
            )
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