package pl.blaszak.ai.demoapiclient

import pl.blaszak.ai.demoapiclient.model.LocalDbMessage
import pl.blaszak.ai.demoapiclient.model.OpenAiChatMessage

fun LocalDbMessage.toChatMessage() = OpenAiChatMessage(localDbRole.aiName, prompt)