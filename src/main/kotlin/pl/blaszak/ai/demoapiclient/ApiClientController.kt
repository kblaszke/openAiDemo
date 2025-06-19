package pl.blaszak.ai.demoapiclient

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.blaszak.ai.demoapiclient.model.Answer
import pl.blaszak.ai.demoapiclient.model.Question
import pl.blaszak.ai.demoapiclient.model.RequestedAnswerType

@RestController
@RequestMapping("/api")
class ApiClientController(
    private val apiService: ApiService,
    private val promptGenerator: PromptGenerator
) {

    @GetMapping("/styles")
    fun styles(): ResponseEntity<List<String>> {
        return ResponseEntity.ok(RequestedAnswerType.entries.map { it.toString() })
    }

    @PostMapping("/ask", consumes = ["application/json"], produces = ["application/json"] )
    fun ask(@RequestBody question: Question): ResponseEntity<Answer> {
        val prompt = promptGenerator.generate(question)
        val answerBody = apiService.askGpt(prompt)
        return ResponseEntity.ok(Answer(answerBody))
    }
}