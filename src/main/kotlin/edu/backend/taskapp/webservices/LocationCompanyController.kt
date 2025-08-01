package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.LocationCompanyInput
import edu.backend.taskapp.dtos.LocationCompanyOutput
import edu.backend.taskapp.services.LocationCompanyService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.locationCompanies}")
class LocationCompanyController(private val locationCompanyService: LocationCompanyService) {

    @GetMapping
    @ResponseBody
    fun findAll() = locationCompanyService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = locationCompanyService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody locationCompanyInput: LocationCompanyInput): LocationCompanyOutput? {
        return locationCompanyService.create(locationCompanyInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody locationCompanyInput: LocationCompanyInput): LocationCompanyOutput? {
        return locationCompanyService.update(locationCompanyInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        locationCompanyService.deleteById(id)
    }
}