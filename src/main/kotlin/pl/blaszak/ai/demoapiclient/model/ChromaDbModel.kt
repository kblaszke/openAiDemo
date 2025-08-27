package pl.blaszak.ai.demoapiclient.model


data class ChromaDbQueryRequest(
    val collection: String,
    val query_embedding: List<Float>,
    val n_results: Int
)

data class ChromaDbQueryResponse(
    val ids: List<List<String>>,
    val embeddings: List<List<Float>>? = null,
    val documents: List<List<String>>,
    val uris: List<List<String>>? = null,
    val included: List<String>? = null,
    val data: Any? = null, // lub null, bo może być puste
    val metadatas: List<List<Metadata>>,
    val distances: List<List<Float>>
)

data class SearchResult(
    val id: String,
    var text: String,
    val documentId: String,
    val position: Int,
    val score: Float
)

data class Metadata(
    val title: String? = null,
    val author: String? = null,
    val fileName: String? = null,
    val chunk_id: String? = null,
    val source: String? = null
)