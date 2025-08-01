package edu.backend.taskapp.services

import edu.backend.taskapp.QualificationRepository
import edu.backend.taskapp.dtos.QualificationInput
import edu.backend.taskapp.dtos.QualificationOutput
import edu.backend.taskapp.dtos.StudentQualificationInput
import edu.backend.taskapp.dtos.StudentQualificationOutput
import edu.backend.taskapp.entities.Qualification
import edu.backend.taskapp.mappers.QualificationMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.Optional

interface StudentQualificationService {
    /**
     * Find all Task
     * @return a list of Users
     */
    fun findAll(): List<StudentQualificationOutput>?

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    fun findById(id: Long): StudentQualificationOutput?

    /**
     * Save and flush a Task entity in the database
     * @param studentQualificationInput
     * @return the user created
     */
    fun create(studentQualificationInput: StudentQualificationInput): StudentQualificationOutput?

    /**
     * Update a Task entity in the database
     * @param studentQualificationInput the dto input for Task
     * @return the new Task created
     */
    fun update(studentQualificationInput: StudentQualificationInput): StudentQualificationOutput?

    /**
     * Delete a Task by id from Database
     * @param id of the Task
     */
    fun deleteById(id: Long)
}

@Service
class AbstractStudentQualificationService(
    @Autowired
    val qualificationRepository: QualificationRepository,
    @Autowired
    val qualificationMapper: QualificationMapper,

    ) : StudentQualificationService {
    //    /**
//     * Find all Task
//     * @return a list of Users
//     */
//    override fun findAll(): List<StudentQualificationOutput>? {
//        return qualificationMapper.qualificationListToQualificationOutputList(
//            qualificationRepository.findAll()
//        )
//    }
//
//    /**
//     * Get one Task by id
//     * @param id of the Task
//     * @return the Task found
//     */
//    @Throws(java.util.NoSuchElementException::class)
//    override fun findById(id: Long): StudentQualificationOutput? {
//        val qualification: Optional<Qualification> = qualificationRepository.findById(id)
//        if (qualification.isEmpty) {
//            throw NoSuchElementException(String.format("The qualificatipm with the id: %s not found!", id))
//        }
//        return qualificationMapper.qualificationToQualificationOutput(
//            qualification.get(),
//        )
//    }
//
//    /**
//     * Save and flush a Task entity in the database
//     * @param qualificationInput
//     * @return the user created
//     */
//    override fun create(qualificationInput: StudentQualificationInput): StudentQualificationOutput? {
//        val qualification: Qualification = qualificationMapper.qualificationInputToQualification(qualificationInput)
//        return qualificationMapper.qualificationToQualificationOutput(
//            qualificationRepository.save(qualification)
//        )
//    }
//
//    /**
//     * Update a Task entity in the database
//     * @param qualificationInput the dto input for Task
//     * @return the new Task created
//     */
//    @Throws(NoSuchElementException::class)
//    override fun update(qualificationInput: StudentQualificationInput): StudentQualificationOutput? {
//        val qualification: Optional<Qualification> = qualificationRepository.findById(qualificationInput.idQualification!!)
//        if (qualification.isEmpty) {
//            throw NoSuchElementException(String.format("The qualificatipm with the id: %s not found!", qualificationInput.idQualification))
//        }
//        val qualificationUpdated: Qualification = qualification.get()
//        qualificationMapper.qualificationInputToQualification(qualificationInput, qualificationUpdated)
//        return qualificationMapper.qualificationToQualificationOutput(qualificationRepository.save(qualificationUpdated))
//    }
//
//    /**
//     * Delete a Task by id from Database
//     * @param id of the Task
//     */
//    @Throws(NoSuchElementException::class)
//    override fun deleteById(id: Long) {
//        if (!qualificationRepository.findById(id).isEmpty) {
//            qualificationRepository.deleteById(id)
//        } else {
//            throw NoSuchElementException(String.format("The qualificatipm with the id: %s not found!", id))
//        }
//    }
    override fun findAll(): List<StudentQualificationOutput>? {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): StudentQualificationOutput? {
        TODO("Not yet implemented")
    }

    override fun create(studentQualificationInput: StudentQualificationInput): StudentQualificationOutput? {
        TODO("Not yet implemented")
    }

    override fun update(studentQualificationInput: StudentQualificationInput): StudentQualificationOutput? {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

}