package pl.blaszak.ai.demoapiclient

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.blaszak.ai.demoapiclient.model.SearchResult

class MergeCloserChunksTest {

    @Test
    fun shouldAddOneElement() {
        // given
        val element = searchResult("one.md", "aaa")
        var searchResults = listOf(element)
        // when
        val closerChunks = searchResults.mergeCloserChunks()
        // then
        assertThat(closerChunks.size).isOne
        assertThat(closerChunks[0]).isEqualTo(element)
    }

    @Test
    fun shouldMerge2documentsWithTheSameDocumentId() {
        // given
        val element1 = searchResult("one.md", "aaa")
        val element2 = searchResult("one.md", "bbb")
        val searchResults = listOf(element1, element2)

        val closerChunks = searchResults.mergeCloserChunks()
        // then
        assertThat(closerChunks.size).isOne
        assertThat(closerChunks[0].text).isEqualTo("aaabbb")
    }

    @Test
    fun keep2ElementsWithDifferentDocumentId() {
        // given
        val element1 = searchResult("one.md", "aaa")
        val element2 = searchResult("two.md", "bbb")
        val searchResults = listOf(element1, element2)

        val closerChunks = searchResults.mergeCloserChunks()
        // then
        assertThat(closerChunks.size).isEqualTo(2)
        assertThat(closerChunks[0]).isEqualTo(element1)
        assertThat(closerChunks[1]).isEqualTo(element2)
    }

    @Test
    fun shouldMergeOnlyDocumentsWithTheSameDocumentId() {
        // given
        val element1 = searchResult("one.md", "aaa")
        val element2 = searchResult("two.md", "bbb")
        val element3 = searchResult("two.md", "ccc")
        val searchResults = listOf(element1, element2, element3)

        val closerChunks = searchResults.mergeCloserChunks()
        // then
        assertThat(closerChunks.size).isEqualTo(2)
        assertThat(closerChunks[0]).isEqualTo(element1)
        assertThat(closerChunks[1].text).isEqualTo("bbbccc")
    }

    private fun searchResult(documentId: String, text: String): SearchResult {
        return SearchResult("", text, documentId, 1, 1.0F)
    }
}