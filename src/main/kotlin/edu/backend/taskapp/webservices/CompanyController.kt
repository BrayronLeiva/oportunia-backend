package edu.backend.taskapp.webservices

import edu.backend.taskapp.LoggedUser
import edu.backend.taskapp.dtos.CompanyImageOutput
import edu.backend.taskapp.dtos.CompanyInput
import edu.backend.taskapp.dtos.CompanyOutput
import edu.backend.taskapp.dtos.StudentMatchResult
import edu.backend.taskapp.dtos.StudentOutput
import edu.backend.taskapp.services.AIService.AIService
import edu.backend.taskapp.services.CompanyService
import edu.backend.taskapp.services.UserService
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("\${url.companies}")
class CompanyController(
    private val companyService: CompanyService,
    private val userService: UserService
    ) {

    /**
     * WS to find all elements of type Company
     * @return A list of elements of type Company
     */
    @GetMapping
    @ResponseBody
    fun findAll() = companyService.findAll()

    /**
     * WS to find one Company by the id
     * @param id to find Company
     * @return the Company found
     */
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = companyService.findById(id)

    /**
     * WS to create a Company
     * @param companyInput the company to create
     * @return the Company created
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody companyInput: CompanyInput): CompanyOutput? {
        return companyService.create(companyInput)
    }

    /**
     * WS to update a Company
     * @param companyInput the company to update
     * @return the Company updated
     */
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody companyInput: CompanyInput): CompanyOutput? {
        return companyService.update(companyInput)
    }

    /**
     * WS to delete a Company by id
     * @param id to identify the company
     */
    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        companyService.deleteById(id)
    }

    @GetMapping("/me")
    @ResponseBody
    fun findUserCompany(): CompanyImageOutput? {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)

        return companyService.findByUserIdwithImage(user?.id ?: throw Exception("No company found"))
    }
}