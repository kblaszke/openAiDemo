package pl.blaszak.ai.demoapiclient.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import pl.blaszak.ai.demoapiclient.exceptions.AiDemoException
import pl.blaszak.ai.demoapiclient.model.OpenAiChatMessage
import pl.blaszak.ai.demoapiclient.model.OpenAiChatRequest
import pl.blaszak.ai.demoapiclient.model.OpenAiChatResponse

class ApiService(val apiKey: String) {

    val objectMapper = jacksonObjectMapper()
    val okHttpClient = OkHttpClient()

    fun chatGpt(messages: List<OpenAiChatMessage>, temperature: Double): String {
        val request = createRequest(messages, temperature)
        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) throw AiDemoException("Unexpected code $response")
        val body = response.body?.string() ?: throw AiDemoException("No response body")
        val openAiChatResponse = objectMapper.readValue(body, OpenAiChatResponse::class.java)
        return openAiChatResponse.choices.first().message.content
    }

    private fun createRequest(messages: List<OpenAiChatMessage>, temperature: Double): Request {
        val payload = OpenAiChatRequest(messages = messages, temperature = temperature)
        val requestBody = objectMapper.writeValueAsString(payload)
        return createRequest(requestBody)
    }

    private fun createRequest(requestBody: String) = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()
}