package edu.backend.taskapp.webservices

import edu.backend.taskapp.LoggedUser
import edu.backend.taskapp.dtos.LocationRequestDTO
import edu.backend.taskapp.dtos.StudentImageOutput
import edu.backend.taskapp.dtos.StudentInput
import edu.backend.taskapp.dtos.StudentMatchResult
import edu.backend.taskapp.dtos.StudentOutput
import edu.backend.taskapp.dtos.StudentQualificationsOutput
import edu.backend.taskapp.services.CompanyService
import edu.backend.taskapp.services.StudentService
import edu.backend.taskapp.services.UserService
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("\${url.students}")
class StudentController(
    private val studentService: StudentService,
    private val companyService: CompanyService,
    private val userService: UserService
) {

    @GetMapping
    @ResponseBody
    fun findAll() = studentService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = studentService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody studentInput: StudentInput): StudentOutput? {
        return studentService.create(studentInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody studentInput: StudentInput): StudentOutput? {
        return studentService.update(studentInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        studentService.deleteById(id)
    }

    //Custom

    /**
     * WS to find the recommend students
     * @param companyId the company to find students
     * @return the list of recommendations
     */
    @GetMapping("/recommendations")
    @ResponseBody
    fun matchStudents(): List<StudentMatchResult> {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)
        val company = companyService.findByUserId(user?.id ?: throw Exception("No company found"))

        return studentService.findRecommendedStudentsByCompany(company!!.idCompany)
    }


    /**
     * WS to find the recommend students
     * @param companyId the company to find students
     * @return the list of recommendations
     */
    @GetMapping("/company/logged")
    @ResponseBody
    fun findStudentsRequesting(): List<StudentOutput> {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)
        val company = companyService.findByUserId(user?.id ?: throw Exception("No company found"))

        return studentService.findStudentsRequestingByCompany(company!!.idCompany)
    }


    @GetMapping("/me")
    @ResponseBody
    fun findUserStudent(): StudentOutput? {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)

        return studentService.findByUserId(user?.id ?: throw Exception("No student found"))
    }

    /**
     * WS to find the recommend students
     * @param companyId the company to find students
     * @return the list of recommendations
     */
    @GetMapping("/qualifications/company")
    @ResponseBody
    fun findStudentsWithTheirQualificationsRequesting(): List<StudentQualificationsOutput> {
        val username = LoggedUser.get()
        val user = userService.findByEmail(username)
        val company = companyService.findByUserId(user?.id ?: throw Exception("No company found"))

        return studentService.findStudentsWithQualificationsRequestingByCompany(company!!.idCompany)
    }
}