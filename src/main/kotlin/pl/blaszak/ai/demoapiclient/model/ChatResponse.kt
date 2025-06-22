package pl.blaszak.ai.demoapiclient.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChatResponse(val choices: List<ChatChoice>)