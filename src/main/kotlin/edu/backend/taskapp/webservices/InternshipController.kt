package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.InternshipInput
import edu.backend.taskapp.dtos.InternshipLocationMatchOutput
import edu.backend.taskapp.dtos.InternshipMatchResult
import edu.backend.taskapp.dtos.InternshipOutput
import edu.backend.taskapp.dtos.LocationRequestDTO
import edu.backend.taskapp.services.InternshipService
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.internships}")
class InternshipController(private val internshipService: InternshipService) {

    /**
     * WS to find all elements of type Internship
     * @return A list of elements of type Internship
     */
    @GetMapping
    @ResponseBody
    fun findAll() = internshipService.findAll()

    /**
     * WS to find one Internship by the id
     * @param id to find Internship
     * @return the Internship found
     */
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = internshipService.findById(id)

    /**
     * WS to create an Internship
     * @param internshipInput the internship to create
     * @return the Internship created
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody internshipInput: InternshipInput): InternshipOutput? {
        return internshipService.create(internshipInput)
    }

    /**
     * WS to update an Internship
     * @param internshipInput the internship to update
     * @return the Internship updated
     */
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody internshipInput: InternshipInput): InternshipOutput? {
        return internshipService.update(internshipInput)
    }

    /**
     * WS to delete an Internship by id
     * @param id to identify the internship
     */
    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        internshipService.deleteById(id)
    }

    /**
     * WS to find one Internship by the id
     * @param id to find Internships by location
     * @return the Internships found by location
     */
    @GetMapping("locations/{id}")
    @ResponseBody
    fun findByLocationId(@PathVariable id: Long) = internshipService.findByLocationId(id)

}