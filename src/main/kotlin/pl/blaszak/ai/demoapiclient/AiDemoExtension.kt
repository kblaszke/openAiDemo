package pl.blaszak.ai.demoapiclient

import pl.blaszak.ai.demoapiclient.model.ChatMessage
import pl.blaszak.ai.demoapiclient.model.Message

fun Message.toChatMessage() = ChatMessage(role.aiName, prompt)