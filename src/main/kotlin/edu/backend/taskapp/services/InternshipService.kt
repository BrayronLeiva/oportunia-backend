package edu.backend.taskapp.services

import edu.backend.taskapp.InternshipLocationRepository
import edu.backend.taskapp.InternshipRepository
import edu.backend.taskapp.LocationCompanyRepository
import edu.backend.taskapp.dtos.InternshipInput
import edu.backend.taskapp.dtos.InternshipLocationMatchOutput
import edu.backend.taskapp.dtos.InternshipMatchResult
import edu.backend.taskapp.dtos.InternshipOutput
import edu.backend.taskapp.dtos.LocationRequestDTO
import edu.backend.taskapp.mappers.CompanyMapper
import edu.backend.taskapp.mappers.InternshipLocationMapper
import edu.backend.taskapp.mappers.InternshipMapper
import edu.backend.taskapp.services.AIService.AIService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface InternshipService {
    fun findAll(): List<InternshipOutput>?
    fun findById(id: Long): InternshipOutput?
    fun create(internshipInput: InternshipInput): InternshipOutput?
    fun update(internshipInput: InternshipInput): InternshipOutput?
    fun deleteById(id: Long)
    fun findByLocationId(id: Long): List<InternshipOutput>?

}

@Service
class AbstractInternshipService(
    @Autowired
    val internshipRepository: InternshipRepository,
    @Autowired
    val internshipMapper: InternshipMapper

    ) : InternshipService {

    override fun findAll(): List<InternshipOutput>? {
        return internshipMapper.internshipListToInternshipOutputList(
            internshipRepository.findAll()
        )
    }

    override fun findById(id: Long): InternshipOutput? {
        val internship = internshipRepository.findById(id)
        if (internship.isEmpty) {
            throw NoSuchElementException("The internship with the id: $id not found!")
        }
        return internshipMapper.internshipToInternshipOutput(internship.get())
    }

    override fun create(internshipInput: InternshipInput): InternshipOutput? {
        val internship = internshipMapper.internshipInputToInternship(internshipInput)
        return internshipMapper.internshipToInternshipOutput(
            internshipRepository.save(internship)
        )
    }

    override fun update(internshipInput: InternshipInput): InternshipOutput? {
        val internship = internshipRepository.findById(internshipInput.idInternship!!)
        if (internship.isEmpty) {
            throw NoSuchElementException("The internship with the id: ${internshipInput.idInternship} not found!")
        }
        val updated = internship.get()
        internshipMapper.internshipInputToInternship(internshipInput, updated)
        return internshipMapper.internshipToInternshipOutput(
            internshipRepository.save(updated)
        )
    }

    override fun deleteById(id: Long) {
        if (!internshipRepository.findById(id).isEmpty) {
            internshipRepository.deleteById(id)
        } else {
            throw NoSuchElementException("The internship with the id: $id not found!")
        }
    }


    override fun findByLocationId(id: Long): List<InternshipOutput>? {
        val internships = internshipRepository.findByLocationCompanyId(id)
        if (internships.isEmpty()) {
            throw NoSuchElementException("The internship with the id: $id not found!")
        }
        return internshipMapper.internshipListToInternshipOutputList(internships)
    }


}