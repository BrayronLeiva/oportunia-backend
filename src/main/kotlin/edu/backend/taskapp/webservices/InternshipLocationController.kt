package edu.backend.taskapp.webservices

import edu.backend.taskapp.InternshipRepository
import edu.backend.taskapp.LoggedUser
import edu.backend.taskapp.dtos.InternshipLocationFlagOutput
import edu.backend.taskapp.dtos.InternshipLocationInput
import edu.backend.taskapp.dtos.InternshipLocationMatchFlagOutput
import edu.backend.taskapp.dtos.InternshipLocationMatchOutput
import edu.backend.taskapp.dtos.InternshipLocationOutput
import edu.backend.taskapp.dtos.LocationRequestDTO
import edu.backend.taskapp.services.InternshipLocationService
import edu.backend.taskapp.services.StudentService
import edu.backend.taskapp.services.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.internshipLocations}")
class InternshipLocationController(
    private val internshipLocationService: InternshipLocationService,
    private val userService: UserService,
    private val studentService: StudentService
) {

    @GetMapping
    @ResponseBody
    fun findAll() = internshipLocationService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = internshipLocationService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody internshipLocationInput: InternshipLocationInput): InternshipLocationOutput? {
        return internshipLocationService.create(internshipLocationInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody internshipLocationInput: InternshipLocationInput): InternshipLocationOutput? {
        return internshipLocationService.update(internshipLocationInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        internshipLocationService.deleteById(id)
    }

    @GetMapping("/location/{locationId}")
    @ResponseBody
    fun findByLocationCompanyId(@PathVariable locationId: Long): List<InternshipLocationOutput> {
        return internshipLocationService.findByLocationCompanyId(locationId)
    }

    @GetMapping("recommendations")
    @ResponseBody
    fun recommendInternshipsLocationsForStudent(): List<InternshipLocationMatchOutput>  {
        val username = LoggedUser.get()

        val user = userService.findByEmail(username)
        val student = studentService.findByUserId(user?.id ?: throw Exception("No student found"))

        return internshipLocationService.findRecommendedInternshipsByStudent(student!!.idStudent)
    }

    @GetMapping("available/recommendations")
    @ResponseBody
    fun recommendInternshipsLocationsAvailableForStudent(): List<InternshipLocationMatchOutput>  {
        val username = LoggedUser.get()

        val user = userService.findByEmail(username)
        val student = studentService.findByUserId(user?.id ?: throw Exception("No student found"))
        //val location = LocationRequestDTO(, lng)

        return internshipLocationService.findRecommendedInternshipsAvailableByStudent(student!!.idStudent)
    }

    @GetMapping("/location/flag-byStudent/{locationId}")
    @ResponseBody
    fun findByLocationFlagByStudentByCompanyId(@PathVariable locationId: Long): List<InternshipLocationFlagOutput> {
        val username = LoggedUser.get()

        val user = userService.findByEmail(username)
        val student = studentService.findByUserId(user?.id ?: throw Exception("No student found"))

        return internshipLocationService.findByLocationCompanyIdAndRequestFlagByStudent(locationId,
            student?.idStudent ?: throw Exception("No student found")
        )
    }

    @GetMapping("/flag/student")
    @ResponseBody
    fun findByLocationFlagByStudent(): List<InternshipLocationFlagOutput> {
        val username = LoggedUser.get()

        val user = userService.findByEmail(username)
        val student = studentService.findByUserId(user?.id ?: throw Exception("No student found"))

        return internshipLocationService.findByRequestFlagByStudent(student?.idStudent ?: throw Exception("No student found")
        )
    }

    @GetMapping("recommendations/flag")
    @ResponseBody
    fun recommendInternshipsLocationsFlagForStudent(): List<InternshipLocationMatchFlagOutput>  {
        val username = LoggedUser.get()

        val user = userService.findByEmail(username)
        val student = studentService.findByUserId(user?.id ?: throw Exception("No student found"))


        return internshipLocationService.findRecommendedInternshipsFlagByStudent(student!!.idStudent)
    }

    @GetMapping("available")
    @ResponseBody
    fun findAllAvailable(): List<InternshipLocationOutput> {
        val username = LoggedUser.get()

        val user = userService.findByEmail(username)
        val student = studentService.findByUserId(user?.id ?: throw Exception("No student found"))
        return internshipLocationService.findAllAvailable(student!!.idStudent)
    }

}