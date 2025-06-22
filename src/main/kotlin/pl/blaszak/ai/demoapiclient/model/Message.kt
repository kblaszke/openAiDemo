package pl.blaszak.ai.demoapiclient.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.NamedQueries
import org.hibernate.annotations.NamedQuery
import java.time.LocalDateTime

@Entity
@NamedQueries(
        NamedQuery(name = "findByConversationId", query = "select m from Message m where m.conversationId = ?1"),
        NamedQuery(name = "deleteOldRecords", query = "delete from Message m where m.creationDateTime <= ?1")
)
class Message(
    @Id @GeneratedValue var id: Long? = null,
    var conversationId: String,
    var role: Role,   // "user", "assistant", "system"
    var prompt: String,
    var creationDateTime: LocalDateTime = LocalDateTime.now()
) {
    constructor(): this(null, "", Role.USER, "")
}

