package pl.blaszak.ai.demoapiclient.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import pl.blaszak.ai.demoapiclient.exceptions.AiDemoException
import pl.blaszak.ai.demoapiclient.model.OpenAiChatMessage
import pl.blaszak.ai.demoapiclient.model.OpenAiChatRequest
import pl.blaszak.ai.demoapiclient.model.OpenAiChatResponse
import pl.blaszak.ai.demoapiclient.model.OpenAiEmbeddingsRequest
import pl.blaszak.ai.demoapiclient.model.OpenAiEmbeddingsResponse

class OpenAiService(val apiKey: String) {

    companion object {
        val BASE_URL = "https://api.openai.com/v1"
        val CHAT_ENDPOINT = "/chat/completions"
        val EMBEDDINGS_ENDPOINT = "/embeddings"
    }

    val objectMapper = jacksonObjectMapper()
    val okHttpClient = OkHttpClient()

    fun chatGpt(messages: List<OpenAiChatMessage>, temperature: Double): String {
        val request = createChatRequest(messages, temperature)
        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) throw AiDemoException("Unexpected code $response")
        val body = response.body?.string() ?: throw AiDemoException("No response body")
        val openAiChatResponse = objectMapper.readValue(body, OpenAiChatResponse::class.java)
        return openAiChatResponse.choices.first().message.content
    }

    fun getEmbeddings(text: String): List<Float> {
        val request = createEmbeddingsRequest(text)
        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) throw AiDemoException("Unexpected code $response")
        val body = response.body?.string() ?: throw AiDemoException("No response body")
        val embeddingsResponse = objectMapper.readValue(body, OpenAiEmbeddingsResponse::class.java)
        val embeddings = embeddingsResponse.data.first().embedding
        return embeddings
    }

    private fun createEmbeddingsRequest(text: String): Request {
        val payload = OpenAiEmbeddingsRequest(input = text)
        val requestBody = objectMapper.writeValueAsString(payload)
        return createRequest(EMBEDDINGS_ENDPOINT, requestBody)
    }

    private fun createChatRequest(messages: List<OpenAiChatMessage>, temperature: Double): Request {
        val payload = OpenAiChatRequest(messages = messages, temperature = temperature)
        val requestBody = objectMapper.writeValueAsString(payload)
        return createRequest(CHAT_ENDPOINT, requestBody)
    }

    private fun createRequest(endpoint: String, requestBody: String) = Request.Builder()
            .url(BASE_URL + endpoint)
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()
}