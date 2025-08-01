package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.RegisterCompanyInput
import edu.backend.taskapp.dtos.RegisterCompanyOutput
import edu.backend.taskapp.dtos.RegisterStudentInput
import edu.backend.taskapp.dtos.RegisterStudentOutput
import edu.backend.taskapp.dtos.StudentInput
import edu.backend.taskapp.dtos.UserInput
import edu.backend.taskapp.dtos.CompanyInput
import edu.backend.taskapp.services.CompanyService
import edu.backend.taskapp.services.StudentService
import edu.backend.taskapp.services.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("\${url.register}")
class RegisterController(
    private val userService: UserService,
    private val studentService: StudentService,
    private val companyService: CompanyService)
{
    @PostMapping("/student", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createStudent(@RequestBody registerInput: RegisterStudentInput): RegisterStudentOutput? {
        val userInput = UserInput(
            firstName = registerInput.firstName,
            lastName = registerInput.lastName,
            email = registerInput.email,
            password = registerInput.password,
            enabled = registerInput.enabled,
            tokenExpired = registerInput.tokenExpired,
        )

        val userOutput = userService.create(userInput)

        val studentInput = StudentInput(
            nameStudent = registerInput.nameStudent,
            identification = registerInput.identification,
            personalInfo = registerInput.personalInfo,
            experience = registerInput.experience,
            ratingStudent = registerInput.ratingStudent,
            homeLatitude = registerInput.homeLatitude,
            homeLongitude = registerInput.homeLongitude,
            imageProfile = registerInput.imageProfile,
            userId = userOutput!!.id,
        )

        val studentOutput = studentService.create(studentInput)

        val registerStudentOutput = RegisterStudentOutput(
            user = userOutput,
            student = studentOutput!!
        )
        return registerStudentOutput
    }

    @PostMapping("/company", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createCompany(@RequestBody registerInput: RegisterCompanyInput): RegisterCompanyOutput? {
        val userInput = UserInput(
            firstName = registerInput.firstName,
            lastName = registerInput.lastName,
            email = registerInput.email,
            password = registerInput.password,
            enabled = registerInput.enabled,
            tokenExpired = registerInput.tokenExpired,
        )

        val userOutput = userService.create(userInput)

        val companyInput = CompanyInput(
            idCompany = registerInput.idCompany,
            nameCompany = registerInput.nameCompany,
            description = registerInput.description,
            history = registerInput.history,
            mision = registerInput.mision,
            vision = registerInput.vision,
            corporateCultur = registerInput.corporateCultur,
            contactCompany = registerInput.contactCompany,
            ratingCompany = registerInput.ratingCompany,
            internshipType = registerInput.internshipType,
            imageProfile = registerInput.imageProfile,
            userId = userOutput?.id,
        )

        val companyOutput = companyService.create(companyInput)

        val registerCompanyOutput = RegisterCompanyOutput(
            user = userOutput!!,
            company = companyOutput!!
        )
        return registerCompanyOutput
    }

    @PostMapping("/student/{id}/upload-image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImageStudent(
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Map<String, String>> {
        val imageUrl = studentService.uploadProfileImage(id, file)
        return ResponseEntity.ok(mapOf("imageUrl" to imageUrl))

    }

    @PostMapping("/company/{id}/upload-image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImageCompany(
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Map<String, String>> {
        val imageUrl = companyService.uploadProfileImage(id, file)
        return ResponseEntity.ok(mapOf("imageUrl" to imageUrl))
    }
}