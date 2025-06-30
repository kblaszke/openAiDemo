package pl.blaszak.ai.demoapiclient.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class OpenAiChatMessage(val role: String,   // "user", "assistant", "system"
                             val content: String)

data class OpenAiChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<OpenAiChatMessage>,
    val temperature: Double = 0.7
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OpenAiChatChoice(val message: OpenAiChatMessage)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OpenAiChatResponse(val choices: List<OpenAiChatChoice>)