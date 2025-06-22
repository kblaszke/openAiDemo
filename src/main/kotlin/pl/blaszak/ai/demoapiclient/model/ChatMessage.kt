package pl.blaszak.ai.demoapiclient.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChatMessage(val role: String,   // "user", "assistant", "system"
                       val content: String)
