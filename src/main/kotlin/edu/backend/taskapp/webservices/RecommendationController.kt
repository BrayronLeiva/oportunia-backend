package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.RecommendationInput
import edu.backend.taskapp.dtos.RecommendationOutput
import edu.backend.taskapp.services.RecommendationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.recommendations}")
class RecommendationController(private val recommendationService: RecommendationService) {

    @GetMapping
    @ResponseBody
    fun findAll() = recommendationService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = recommendationService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody recommendationInput: RecommendationInput): RecommendationOutput? {
        return recommendationService.create(recommendationInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody recommendationInput: RecommendationInput): RecommendationOutput? {
        return recommendationService.update(recommendationInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        recommendationService.deleteById(id)
    }
}