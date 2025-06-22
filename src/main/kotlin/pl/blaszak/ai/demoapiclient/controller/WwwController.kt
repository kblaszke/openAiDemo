package pl.blaszak.ai.demoapiclient.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import pl.blaszak.ai.demoapiclient.model.Role
import pl.blaszak.ai.demoapiclient.service.ChatService
import java.util.UUID


@Controller
class WwwController(val chatService: ChatService) {
    companion object {
        const val CONVERSATION_ID = "conversationId"
        const val ROLE = "role"
        const val PROMPT = "prompt"
        const val ANSWER = "answer"
    }

    @GetMapping("/")
    fun index(): ModelAndView {
        val model = ModelAndView()
        model.addObject(CONVERSATION_ID, "")
        model.addObject(ANSWER, "")
        model.viewName = "index"
        return model
    }

    @PostMapping("/")
    fun postIndex(@RequestParam requestParams: Map<String, String>) : ModelAndView {
        val conversationId = if(requestParams[CONVERSATION_ID].isNullOrEmpty()) UUID.randomUUID().toString() else requestParams[CONVERSATION_ID].toString()
        val role = Role.valueOf(requestParams[ROLE]!!)
        val prompt = requestParams[PROMPT]
        val model = ModelAndView()
        val answer = chatService.handle(conversationId, role, prompt)
        model.addObject(ANSWER, answer)
        model.addObject(CONVERSATION_ID, conversationId)
        model.viewName = "index"
        return model
    }

}

