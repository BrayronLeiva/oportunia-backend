package edu.backend.taskapp.webservices

import edu.backend.taskapp.LoggedUser
import edu.backend.taskapp.dtos.RequestInput
import edu.backend.taskapp.dtos.RequestOutput
import edu.backend.taskapp.dtos.RequestStudentInput
import edu.backend.taskapp.services.CompanyService
import edu.backend.taskapp.services.RequestService
import edu.backend.taskapp.services.StudentService
import edu.backend.taskapp.services.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.requests}")
class RequestController(
    private val requestService: RequestService,
    private val userService: UserService,
    private val studentService: StudentService,
    private val companyService: CompanyService
) {

    @GetMapping
    @ResponseBody
    fun findAll() = requestService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = requestService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody requestStudentInput: RequestStudentInput): RequestOutput? {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)
        val studentId = studentService.findByUserId(user?.id ?: throw Exception("No student found"))?.idStudent

        return requestService.create(RequestInput(0,false, studentId, requestStudentInput.internshipLocationId))
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody requestInput: RequestInput): RequestOutput? {
        return requestService.update(requestInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        requestService.deleteById(id)
    }

    /**
     * WS to find one Internship by the id
     * @param id to find Internships by location
     * @return the Internships found by location
     */
    @GetMapping("students/{id}")
    @ResponseBody
    fun findByStudentId(@PathVariable id: Long) = requestService.findByStudentId(id)

    /**
     * WS to find one Internship by the id
     * @param id to find Internships by location
     * @return the Internships found by location
     */
    @GetMapping("students/{idStudent}/by-company")
    @ResponseBody
    fun findByStudentIdAndCompanyId(@PathVariable idStudent: Long): List<RequestOutput> {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)
        val company = companyService.findByUserId(user?.id ?: throw Exception("No company found"))

        return requestService.findByStudentIdAndCompanyId(idStudent, company!!.idCompany)
    }

    @DeleteMapping("/internship-locations/{id}")
    @ResponseBody
    fun deleteByInternshipLocationIdAndStudentId(@PathVariable id: Long) {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)
        val studentId = studentService.findByUserId(user?.id ?: throw Exception("No student found"))?.idStudent

        requestService.deleteByInternshipLocationIdAndStundentId(id, studentId!!)
    }

}