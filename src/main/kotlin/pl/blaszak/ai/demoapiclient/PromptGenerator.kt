package pl.blaszak.ai.demoapiclient

import pl.blaszak.ai.demoapiclient.model.Question
import pl.blaszak.ai.demoapiclient.model.RequestedAnswerType

class PromptGenerator {

    fun generate(question: Question) = question.body + createPostfix(question.type)

    private fun createPostfix(type: RequestedAnswerType) = when (type) {
        RequestedAnswerType.BIBLICAL -> " Odpowiedź sformułuj w stylu biblijnym"
        RequestedAnswerType.SCIENTIFIC -> " Odpowiedź sformułuj w trudnym do zrozumienia, stylu naukowym"
        else -> " Odpowiedź sformułuj w trudnym do zrozumienia, stylu filozoficznym"
    }

}