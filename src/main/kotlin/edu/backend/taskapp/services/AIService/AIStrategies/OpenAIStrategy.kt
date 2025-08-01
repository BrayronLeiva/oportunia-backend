package edu.backend.taskapp.services.AIService.AIStrategies

import edu.backend.taskapp.dtos.CompanyOutput
import edu.backend.taskapp.dtos.InternshipEvaluateOutput
import edu.backend.taskapp.dtos.InternshipMatchResult
import edu.backend.taskapp.dtos.OpenAIResponse
import edu.backend.taskapp.dtos.StudentMatchResult
import edu.backend.taskapp.dtos.StudentOutput
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class OpenAIBasedStrategy(
    @Value("\${openAiApiKey}") private val apiKey: String
) : AIConnectionStrategy {

    private val webClient = WebClient.builder()
        .baseUrl("https://api.openai.com/v1/chat/completions")
        .defaultHeader("Authorization", "Bearer $apiKey")
        .defaultHeader("Content-Type", "application/json")
        .build()

    override fun make_request(
        prompt: String,
        behaviour: String
    ): String? {
        val request = mapOf(
            "model" to "gpt-4o-mini",
            "messages" to listOf(
                mapOf("role" to "system", "content" to behaviour),
                mapOf("role" to "user", "content" to prompt)
            ),
            "temperature" to 0.4
        )

        return webClient.post()
            .bodyValue(request)
            .retrieve()
            .bodyToMono(OpenAIResponse::class.java)
            .block()
            ?.choices?.firstOrNull()?.message?.content

    }


}
