package pl.blaszak.ai.demoapiclient.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import pl.blaszak.ai.demoapiclient.exceptions.AiDemoException
import pl.blaszak.ai.demoapiclient.model.ChromaDbQueryRequest
import pl.blaszak.ai.demoapiclient.model.ChromaDbQueryResponse
import pl.blaszak.ai.demoapiclient.model.SearchResult
import pl.blaszak.ai.demoapiclient.toSearchResults

class ChromaDbService(val chromaUrl: String) {

    val objectMapper = jacksonObjectMapper()
    val okHttpClient = OkHttpClient()

    fun querySimilarDocuments(collection: String, embedding: List<Float>, topK: Int = 5): List<SearchResult> {
        val request = createRequest(collection, embedding, topK)
        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) throw AiDemoException("Unexpected code $response")
        val body = response.body?.string() ?: throw AiDemoException("No response body")
        val chromaDbResponse = objectMapper.readValue(body, ChromaDbQueryResponse::class.java)
        return chromaDbResponse.toSearchResults()
    }

    private fun createRequest(collection: String, embedding: List<Float>, topK: Int = 5): Request {
        val payload = ChromaDbQueryRequest(collection, embedding, topK)
        val requestBody = objectMapper.writeValueAsString(payload)
        return createRequest(requestBody)
    }

    private fun createRequest(requestBody: String) = Request.Builder()
        .url("$chromaUrl/query")
        .post(requestBody.toRequestBody("application/json".toMediaType()))
        .build()
}