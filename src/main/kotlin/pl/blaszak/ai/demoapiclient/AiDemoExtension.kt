package pl.blaszak.ai.demoapiclient

import pl.blaszak.ai.demoapiclient.model.ChromaDbQueryResponse
import pl.blaszak.ai.demoapiclient.model.LocalDbMessage
import pl.blaszak.ai.demoapiclient.model.OpenAiChatMessage
import pl.blaszak.ai.demoapiclient.model.SearchResult

fun LocalDbMessage.toChatMessage() = OpenAiChatMessage(localDbRole.aiName, prompt)
fun ChromaDbQueryResponse.toSearchResults() = ids.first().indices.map { i ->
    SearchResult(
        id = ids.first()[i],
        text = documents.first()[i],
        documentId = metadatas.first()[i].fileName ?: "unknown",
        position = metadatas.first()[i].chunk_id?.toInt() ?: -1,
        score = distances.first()[i]
    )
}

fun List<SearchResult>.mergeCloserChunks(): List<SearchResult> =
    sortedBy { it.score }.fold(mutableListOf<SearchResult>()) { merged, element ->
        if (merged.isEmpty() || merged.last().documentId != element.documentId) {
            merged.add(element)
        } else {
            val last = merged.removeAt(merged.lastIndex)
            merged.add(
                SearchResult(
                    last.id,
                    last.text + element.text,
                    last.documentId,
                    last.position,
                    last.score
                )
            )
        }
        merged
    }