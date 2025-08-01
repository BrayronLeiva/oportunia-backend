package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.RatingCompanyStudentInput
import edu.backend.taskapp.dtos.RatingCompanyStudentOutput
import edu.backend.taskapp.services.RatingCompanyStudentService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.ratingCompanyStudents}")
class RatingCompanyStudentController(private val ratingCompanyStudentService: RatingCompanyStudentService) {

    @GetMapping
    @ResponseBody
    fun findAll() = ratingCompanyStudentService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = ratingCompanyStudentService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody ratingCompanyStudentInput: RatingCompanyStudentInput): RatingCompanyStudentOutput? {
        return ratingCompanyStudentService.create(ratingCompanyStudentInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody ratingCompanyStudentInput: RatingCompanyStudentInput): RatingCompanyStudentOutput? {
        return ratingCompanyStudentService.update(ratingCompanyStudentInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        ratingCompanyStudentService.deleteById(id)
    }
}