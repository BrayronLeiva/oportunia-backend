package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.QuestionInput
import edu.backend.taskapp.dtos.QuestionOutput
import edu.backend.taskapp.services.QuestionService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.questions}")
class QuestionController(private val questionService: QuestionService) {

    @GetMapping
    @ResponseBody
    fun findAll() = questionService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = questionService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody questionInput: QuestionInput): QuestionOutput? {
        return questionService.create(questionInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody questionInput: QuestionInput): QuestionOutput? {
        return questionService.update(questionInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        questionService.deleteById(id)
    }
}