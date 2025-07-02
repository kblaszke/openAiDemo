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

data class OpenAiEmbeddingsRequest(
    val model: String = "text-embedding-3-small",
    val input: String
)

data class OpenAiEmbeddingsResponse(
    val `object`: String,
    val data: List<EmbeddingData>,
    val model: String,
    val usage: Usage
)

data class EmbeddingData(
    val `object`: String,
    val index: Int,
    val embedding: List<Float>
)

data class Usage(
    val prompt_tokens: Int,
    val total_tokens: Int
)
