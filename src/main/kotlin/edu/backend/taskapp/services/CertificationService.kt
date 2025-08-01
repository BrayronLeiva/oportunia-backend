package edu.backend.taskapp.services

import edu.backend.taskapp.CertificationRepository
import edu.backend.taskapp.StudentRepository
import edu.backend.taskapp.dtos.CertificationInput
import edu.backend.taskapp.dtos.CertificationOutput
import edu.backend.taskapp.mappers.CertificationMapper
import edu.backend.taskapp.entities.Certification
import edu.backend.taskapp.entities.Company
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface CertificationService {
    /**
     * Find all Task
     * @return a list of Users
     */
    fun findAll(): List<CertificationOutput>?

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    fun findById(id: Long): CertificationOutput?

    /**
     * Save and flush a Task entity in the database
     * @param certificationInput
     * @return the user created
     */
    fun create(certificationInput: CertificationInput): CertificationOutput?

    /**
     * Update a Task entity in the database
     * @param certificationInput the dto input for Task
     * @return the new Task created
     */
    fun update(certificationInput: CertificationInput): CertificationOutput?

    /**
     * Delete a Task by id from Database
     * @param id of the Task
     */
    fun deleteById(id: Long)
}

@Service
class AbstractCertificationService(
    @Autowired
    val studentRepository: StudentRepository,
    @Autowired
    val certificationRepository: CertificationRepository,
    @Autowired
    val certificationMapper: CertificationMapper,
) : CertificationService {
    /**
     * Find all Task
     * @return a list of Users
     */
    override fun findAll(): List<CertificationOutput>? {
        return certificationMapper.certificationListToCertificationOutputList(
            certificationRepository.findAll()
        )
    }

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): CertificationOutput? {
        val certification: Optional<Certification> = certificationRepository.findById(id)
        if (certification.isEmpty) {
            throw NoSuchElementException(String.format("The Certification with the id: %s not found!", id))
        }
        return certificationMapper.certificationToCertificationOutput(
            certification.get(),
        )
    }

    /**
     * Save and flush a Task entity in the database
     * @param certificationInput
     * @return the user created
     */
    override fun create(certificationInput: CertificationInput): CertificationOutput? {
        val student = studentRepository.findById(certificationInput.studentId!!)
            .orElseThrow { NoSuchElementException("Student with ID ${certificationInput.studentId} not found") }

        val certification: Certification = certificationMapper.certificationInputToCertification(certificationInput, student)
        certification.student = student

        return certificationMapper.certificationToCertificationOutput(
            certificationRepository.save(certification)
        )
    }

    /**
     * Update a Task entity in the database
     * @param certificationInput the dto input for Task
     * @return the new Task created
     */
    @Throws(NoSuchElementException::class)
    override fun update(certificationInput: CertificationInput): CertificationOutput? {
        val certification: Optional<Certification> = certificationRepository.findById(certificationInput.idCertification!!)
        if (certification.isEmpty) {
            throw NoSuchElementException(String.format("The Certification with the id: %s not found!", certificationInput.idCertification))
        }
        val certificationUpdated: Certification = certification.get()
        certificationMapper.certificationInputToCertification(certificationInput, certificationUpdated)
        return certificationMapper.certificationToCertificationOutput(certificationRepository.save(certificationUpdated))
    }

    /**
     * Delete a Task by id from Database
     * @param id of the Task
     */
    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!certificationRepository.findById(id).isEmpty) {
            certificationRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The Certification with the id: %s not found!", id))
        }
    }

}