package pl.blaszak.ai.demoapiclient.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import org.hibernate.annotations.NamedQueries
import org.hibernate.annotations.NamedQuery
import java.time.LocalDateTime

enum class LocalDbRole(val aiName: String) {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system")
}

@Entity
@NamedQueries(
    NamedQuery(name = "findByConversationId", query = "select m from LocalDbMessage m where m.conversationId = ?1"),
    NamedQuery(name = "deleteOldRecords", query = "delete from LocalDbMessage m where m.creationDateTime <= ?1")
)
class LocalDbMessage(
    @Id @GeneratedValue var id: Long? = null,
    var conversationId: String,
    var localDbRole: LocalDbRole,   // "user", "assistant", "system"
    @Lob
    var prompt: String,
    var creationDateTime: LocalDateTime = LocalDateTime.now()
) {
    constructor(): this(null, "", LocalDbRole.USER, "")
}