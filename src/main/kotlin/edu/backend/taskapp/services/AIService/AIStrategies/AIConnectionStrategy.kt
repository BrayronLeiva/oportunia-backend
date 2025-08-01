package edu.backend.taskapp.services.AIService.AIStrategies

interface AIConnectionStrategy {
    fun make_request(prompt: String, behaviour: String): String?
}
