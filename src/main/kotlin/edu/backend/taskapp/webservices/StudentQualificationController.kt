package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.QualificationInput
import edu.backend.taskapp.dtos.QualificationOutput
import edu.backend.taskapp.services.QualificationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.studentQualifications}")
class StudentQualificationController(private val qualificationService: QualificationService) {

    @GetMapping
    @ResponseBody
    fun findAll() = qualificationService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = qualificationService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody qualificationInput: QualificationInput): QualificationOutput? {
        return qualificationService.create(qualificationInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody qualificationInput: QualificationInput): QualificationOutput? {
        return qualificationService.update(qualificationInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        qualificationService.deleteById(id)
    }
}