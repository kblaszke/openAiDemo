package pl.blaszak.ai.demoapiclient

import org.springframework.data.repository.CrudRepository
import pl.blaszak.ai.demoapiclient.model.Message
import java.time.LocalDateTime

interface MessageRepository: CrudRepository<Message, Long> {

    fun findByConversationId(conversationId: String): List<Message>
}