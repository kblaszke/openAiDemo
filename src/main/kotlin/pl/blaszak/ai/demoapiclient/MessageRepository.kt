package pl.blaszak.ai.demoapiclient

import org.springframework.data.repository.CrudRepository
import pl.blaszak.ai.demoapiclient.model.LocalDbMessage

interface MessageRepository: CrudRepository<LocalDbMessage, Long> {

    fun findByConversationId(conversationId: String): List<LocalDbMessage>
}