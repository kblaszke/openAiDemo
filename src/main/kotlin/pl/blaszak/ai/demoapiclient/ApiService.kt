package pl.blaszak.ai.demoapiclient

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ApiService(val apiKey: String) {

    val client = HttpClient.newHttpClient()

    fun askGpt(prompt: String): String {
        val request = mapPromptToRequest(prompt)
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val json = jacksonObjectMapper().readTree(response.body())
        return json["choices"][0]["message"]["content"].asText()
    }

    private fun mapPromptToRequest(prompt: String): HttpRequest? {
        val payload = payload(prompt)
        return request(payload)
    }

    private fun payload(prompt: String) = mapOf(
        "model" to "gpt-3.5-turbo",
        "messages" to listOf(mapOf("role" to "user", "content" to prompt)),
        "temperature" to 0.7
    )

    private fun request(payload: Map<String, Any>) = HttpRequest.newBuilder()
        .uri(URI.create("https://api.openai.com/v1/chat/completions"))
        .header("Authorization", "Bearer $apiKey")
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(jacksonObjectMapper().writeValueAsString(payload)))
        .build()
}