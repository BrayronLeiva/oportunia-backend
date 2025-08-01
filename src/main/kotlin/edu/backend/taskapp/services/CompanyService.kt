package edu.backend.taskapp.services

import com.cloudinary.Cloudinary
import edu.backend.taskapp.CompanyRepository
import edu.backend.taskapp.StudentRepository
import edu.backend.taskapp.UserRepository
import edu.backend.taskapp.dtos.CompanyImageOutput
import edu.backend.taskapp.dtos.CompanyInput
import edu.backend.taskapp.dtos.CompanyOutput
import edu.backend.taskapp.dtos.StudentMatchResult
import edu.backend.taskapp.dtos.StudentOutput
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.Student
import edu.backend.taskapp.mappers.CompanyMapper
import edu.backend.taskapp.mappers.StudentMapper
import edu.backend.taskapp.services.AIService.AIService
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface CompanyService {
    fun findAll(): List<CompanyOutput>?
    fun findById(id: Long): CompanyOutput?
    fun create(companyInput: CompanyInput): CompanyOutput?
    fun update(companyInput: CompanyInput): CompanyOutput?
    fun deleteById(id: Long)
    fun findByUserId(userId: Long): CompanyOutput?
    fun findByUserIdwithImage(userId: Long): CompanyImageOutput?
    fun uploadProfileImage(companyId: Long, file: MultipartFile): String
}

@Service
class AbstractCompanyService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val companyRepository: CompanyRepository,
    @Autowired
    val companyMapper: CompanyMapper,
    @Autowired
    val studentRepository: StudentRepository,
    @Autowired
    val studentMapper: StudentMapper,
    @Autowired
    val aiService: AIService,
    @Autowired
    private val cloudinary: Cloudinary
) : CompanyService {

    override fun findAll(): List<CompanyOutput>? {
        return companyMapper.companyListToCompanyOutputList(
            companyRepository.findAll()
        )
    }

    override fun findById(id: Long): CompanyOutput? {
        val company: Optional<Company> = companyRepository.findById(id)
        if (company.isEmpty) {
            throw NoSuchElementException("The company with the id: $id not found!")
        }
        return companyMapper.companyToCompanyOutput(company.get())
    }

    override fun create(companyInput: CompanyInput): CompanyOutput? {
        val user = userRepository.findById(companyInput.userId!!)
            .orElseThrow { NoSuchElementException("User with ID ${companyInput.userId} not found") }

        val company: Company = companyMapper.companyInputToCompany(companyInput, user)
        company.user = user

        return companyMapper.companyToCompanyOutput(
            companyRepository.save(company)
        )
    }

    override fun update(companyInput: CompanyInput): CompanyOutput? {
        val existing: Optional<Company> = companyRepository.findById(companyInput.idCompany!!)
        if (existing.isEmpty) {
            throw NoSuchElementException("The company with the id: ${companyInput.idCompany} not found!")
        }
        val updated: Company = existing.get()
        companyMapper.companyInputToCompany(companyInput, updated)
        return companyMapper.companyToCompanyOutput(
            companyRepository.save(updated)
        )
    }

    override fun deleteById(id: Long) {
        if (!companyRepository.findById(id).isEmpty) {
            companyRepository.deleteById(id)
        } else {
            throw NoSuchElementException("The company with the id: $id not found!")
        }
    }

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    @Throws(NoSuchElementException::class)
    override fun findByUserId(userId: Long): CompanyOutput? {

        val company: Optional<Company> = companyRepository.findByUserId(userId)
        if (company.isEmpty) {
            throw NoSuchElementException(String.format("The company with the user id: %s not found!", userId))
        }
        return companyMapper.companyToCompanyOutput(
            company.get(),
        )
    }

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    @Throws(NoSuchElementException::class)
    override fun findByUserIdwithImage(userId: Long): CompanyImageOutput? {

        val company: Optional<Company> = companyRepository.findByUserId(userId)
        if (company.isEmpty) {
            throw NoSuchElementException(String.format("The company with the user id: %s not found!", userId))
        }
        return companyMapper.companyToCompanyImageOutput(
            company.get(),
        )
    }

    override fun uploadProfileImage(companyId: Long, file: MultipartFile): String {
        val company = companyRepository.findById(companyId)
            .orElseThrow { RuntimeException("Compania no encontrada") }

        val uploadResult = cloudinary.uploader().upload(file.bytes, mapOf<String, Any>())
        val imageUrl = uploadResult["secure_url"] as String

        val updatedCompany = company.copy(imageProfile = imageUrl)
        companyRepository.save(updatedCompany)


        return imageUrl
    }



}