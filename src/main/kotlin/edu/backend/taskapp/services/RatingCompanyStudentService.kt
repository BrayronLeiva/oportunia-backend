package edu.backend.taskapp.services

import edu.backend.taskapp.CompanyRepository
import edu.backend.taskapp.dtos.RatingCompanyStudentInput
import edu.backend.taskapp.dtos.RatingCompanyStudentOutput
import edu.backend.taskapp.mappers.RatingCompanyStudentMapper
import edu.backend.taskapp.RatingCompanyStudentRepository
import edu.backend.taskapp.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface RatingCompanyStudentService {
    fun findAll(): List<RatingCompanyStudentOutput>?
    fun findById(id: Long): RatingCompanyStudentOutput?
    fun create(ratingCompanyStudentInput: RatingCompanyStudentInput): RatingCompanyStudentOutput?
    fun update(ratingCompanyStudentInput: RatingCompanyStudentInput): RatingCompanyStudentOutput?
    fun deleteById(id: Long)
}

@Service
class AbstractRatingCompanyStudentService(
    @Autowired val studentRepository: StudentRepository,
    @Autowired val companyRepository: CompanyRepository,
    @Autowired val ratingCompanyStudentRepository: RatingCompanyStudentRepository,
    @Autowired val ratingCompanyStudentMapper: RatingCompanyStudentMapper
) : RatingCompanyStudentService {

    override fun findAll(): List<RatingCompanyStudentOutput>? {
        return ratingCompanyStudentMapper.ratingCompanyStudentListToRatingCompanyStudentOutputList(
            ratingCompanyStudentRepository.findAll()
        )
    }

    override fun findById(id: Long): RatingCompanyStudentOutput? {
        val ratingCompanyStudent = ratingCompanyStudentRepository.findById(id)
        if (ratingCompanyStudent.isEmpty) {
            throw NoSuchElementException("The rating company student with the id: $id not found!")
        }
        return ratingCompanyStudentMapper.ratingCompanyStudentToRatingCompanyStudentOutput(ratingCompanyStudent.get())
    }

    override fun create(ratingCompanyStudentInput: RatingCompanyStudentInput): RatingCompanyStudentOutput? {
        val student = studentRepository.findById(ratingCompanyStudentInput.studentId!!)
            .orElseThrow { NoSuchElementException("Student with ID ${ratingCompanyStudentInput.studentId} not found") }

        val company = companyRepository.findById(ratingCompanyStudentInput.companyId!!)
            .orElseThrow { NoSuchElementException("Company with ID ${ratingCompanyStudentInput.companyId} not found") }

        val ratingCompanyStudent = ratingCompanyStudentMapper.ratingCompanyStudentInputToRatingCompanyStudent(
            ratingCompanyStudentInput, student, company
        )
        ratingCompanyStudent.student = student
        ratingCompanyStudent.company = company

        val savedRating = ratingCompanyStudentRepository.save(ratingCompanyStudent)
        updateAverageRating(ratingCompanyStudentInput.type!!, student.idStudent!!, company.idCompany!!)
        return ratingCompanyStudentMapper.ratingCompanyStudentToRatingCompanyStudentOutput(savedRating)
    }

    override fun update(ratingCompanyStudentInput: RatingCompanyStudentInput): RatingCompanyStudentOutput? {
        val ratingCompanyStudent = ratingCompanyStudentRepository.findById(ratingCompanyStudentInput.idRating!!)
            .orElseThrow {
                NoSuchElementException("The rating company student with the id: ${ratingCompanyStudentInput.idRating} not found!")
            }

        ratingCompanyStudentMapper.ratingCompanyStudentInputToRatingCompanyStudent(
            ratingCompanyStudentInput, ratingCompanyStudent
        )

        val savedRating = ratingCompanyStudentRepository.save(ratingCompanyStudent)
        updateAverageRating(ratingCompanyStudentInput.type!!, ratingCompanyStudent.student.idStudent!!, ratingCompanyStudent.company.idCompany!!)
        return ratingCompanyStudentMapper.ratingCompanyStudentToRatingCompanyStudentOutput(savedRating)
    }

    override fun deleteById(id: Long) {
        val optionalRating = ratingCompanyStudentRepository.findById(id)
        if (optionalRating.isPresent) {
            val rating = optionalRating.get()
            ratingCompanyStudentRepository.deleteById(id)
            updateAverageRating(rating.type, rating.student.idStudent!!, rating.company.idCompany!!)
        } else {
            throw NoSuchElementException("The rating company student with the id: $id not found!")
        }
    }

    private fun updateAverageRating(type: String, studentId: Long, companyId: Long) {
        when (type) {
            "STU" -> {
                val ratings = ratingCompanyStudentRepository.findAll()
                    .filter { it.student.idStudent == studentId && it.type == "STU" }
                val avgRating = if (ratings.isEmpty()) 0.0 else ratings.map { it.rating }.average()
                val student = studentRepository.findById(studentId).orElseThrow()
                student.ratingStudent = avgRating
                studentRepository.save(student)
            }

            "COM" -> {
                val ratings = ratingCompanyStudentRepository.findAll()
                    .filter { it.company.idCompany == companyId && it.type == "COM" }
                val avgRating = if (ratings.isEmpty()) 0.0 else ratings.map { it.rating }.average()
                val company = companyRepository.findById(companyId).orElseThrow()
                company.ratingCompany = avgRating
                companyRepository.save(company)
            }

            else -> throw IllegalArgumentException("Invalid rating type: $type")
        }
    }
}