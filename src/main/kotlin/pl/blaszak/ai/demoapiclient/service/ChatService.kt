package pl.blaszak.ai.demoapiclient.service

import pl.blaszak.ai.demoapiclient.MessageRepository
import pl.blaszak.ai.demoapiclient.model.LocalDbMessage
import pl.blaszak.ai.demoapiclient.model.LocalDbRole
import pl.blaszak.ai.demoapiclient.toChatMessage

class ChatService(val messageRepository: MessageRepository,
                  val openAiService: OpenAiService,
                  val chromaDbService: ChromaDbService,
                  val collectionName: String
) {

    fun handle(
        conversationId: String,
        localDbRole: LocalDbRole,
        question: String?
    ) = if (question.isNullOrEmpty()) "" else {
        val prompt = createPrompt(localDbRole, question)
        val localDbMessage = LocalDbMessage(null, conversationId, localDbRole, prompt)
        messageRepository.save<LocalDbMessage>(localDbMessage)
        val messages = messageRepository.findByConversationId(conversationId)
        openAiService.chatGpt(messages.map{it.toChatMessage()}, 0.7)
    }

    private fun createPrompt(role: LocalDbRole, question: String): String {
        return when(role) {
            LocalDbRole.USER -> {
                val embeddings = openAiService.getEmbeddings(question)
                val similarDocuments = chromaDbService.querySimilarDocuments(collectionName, embeddings)
                return createPrompt(question, similarDocuments.joinToString(separator = "\n\n"))
            }
            else -> question
        }
    }

    private fun createPrompt(question: String, fragments: String) =
        """Odpowiedz na pytanie na podstawie poniższych fragmentów

Fragmenty:
$fragments
      
Pytanie:
$question
        
""".trimIndent()
}