package pl.blaszak.ai.demoapiclient.service

import pl.blaszak.ai.demoapiclient.MessageRepository
import pl.blaszak.ai.demoapiclient.model.Message
import pl.blaszak.ai.demoapiclient.model.Role
import pl.blaszak.ai.demoapiclient.toChatMessage

class ChatService(val messageRepository: MessageRepository, val apiService: ApiService) {

    fun handle(
        conversationId: String,
        role: Role,
        prompt: String?
    ) = if (prompt.isNullOrEmpty()) "" else {
        val message = Message(null, conversationId, role, prompt)
        messageRepository.save<Message>(message)
        val messages = messageRepository.findByConversationId(conversationId)
        apiService.chatGpt(messages.map{it.toChatMessage()}, 0.7)
    }
}