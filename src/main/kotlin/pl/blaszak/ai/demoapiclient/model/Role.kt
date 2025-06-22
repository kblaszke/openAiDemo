package pl.blaszak.ai.demoapiclient.model

enum class Role(val aiName: String) {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system")
}