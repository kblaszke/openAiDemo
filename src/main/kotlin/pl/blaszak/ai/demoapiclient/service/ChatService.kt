package pl.blaszak.ai.demoapiclient.service

import pl.blaszak.ai.demoapiclient.MessageRepository
import pl.blaszak.ai.demoapiclient.model.LocalDbMessage
import pl.blaszak.ai.demoapiclient.model.LocalDbRole
import pl.blaszak.ai.demoapiclient.toChatMessage

class ChatService(val messageRepository: MessageRepository, val apiService: ApiService) {

    fun handle(
        conversationId: String,
        localDbRole: LocalDbRole,
        prompt: String?
    ) = if (prompt.isNullOrEmpty()) "" else {
        val localDbMessage = LocalDbMessage(null, conversationId, localDbRole, prompt)
        messageRepository.save<LocalDbMessage>(localDbMessage)
        val messages = messageRepository.findByConversationId(conversationId)
        apiService.chatGpt(messages.map{it.toChatMessage()}, 0.7)
    }
}