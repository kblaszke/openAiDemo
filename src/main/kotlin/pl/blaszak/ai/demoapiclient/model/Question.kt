package pl.blaszak.ai.demoapiclient.model

data class Question(val type: RequestedAnswerType = RequestedAnswerType.BIBLICAL, val body: String)
