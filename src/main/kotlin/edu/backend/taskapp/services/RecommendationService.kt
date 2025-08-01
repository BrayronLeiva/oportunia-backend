package edu.backend.taskapp.services

import edu.backend.taskapp.CompanyRepository
import edu.backend.taskapp.dtos.RecommendationInput
import edu.backend.taskapp.dtos.RecommendationOutput
import edu.backend.taskapp.mappers.RecommendationMapper
import edu.backend.taskapp.RecommendationRepository
import edu.backend.taskapp.StudentRepository
import edu.backend.taskapp.entities.Recommendation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional


interface RecommendationService {
    /**
     * Find all Task
     * @return a list of Users
     */
    fun findAll(): List<RecommendationOutput>?

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    fun findById(id: Long): RecommendationOutput?

    /**
     * Save and flush a Task entity in the database
     * @param recommendationInput
     * @return the user created
     */
    fun create(recommendationInput: RecommendationInput): RecommendationOutput?

    /**
     * Update a Task entity in the database
     * @param recommendationInput the dto input for Task
     * @return the new Task created
     */
    fun update(recommendationInput: RecommendationInput): RecommendationOutput?

    /**
     * Delete a Task by id from Database
     * @param id of the Task
     */
    fun deleteById(id: Long)
}

@Service
class AbstractRecommendationService(
    @Autowired
    val studentRepository: StudentRepository,
    @Autowired
    val companyRepository: CompanyRepository,
    @Autowired
    val recommendationRepository: RecommendationRepository,
    @Autowired
    val recommendationMapper: RecommendationMapper,

    ) : RecommendationService {
    /**
     * Find all Task
     * @return a list of Users
     */
    override fun findAll(): List<RecommendationOutput>? {
        return recommendationMapper.recommendationListToRecommendationOutputList(
            recommendationRepository.findAll()
        )
    }

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): RecommendationOutput? {
        val recommendation: Optional<Recommendation> = recommendationRepository.findById(id)
        if (recommendation.isEmpty) {
            throw NoSuchElementException(String.format("The recommendation with the id: %s not found!", id))
        }
        return recommendationMapper.recommendationToRecommendationOutput(
            recommendation.get(),
        )
    }

    /**
     * Save and flush a Task entity in the database
     * @param recommendationInput
     * @return the user created
     */
    override fun create(recommendationInput: RecommendationInput): RecommendationOutput? {
        val student = studentRepository.findById(recommendationInput.studentId!!)
            .orElseThrow { NoSuchElementException("Student with ID ${recommendationInput.studentId} not found") }

        val company = companyRepository.findById(recommendationInput.companyId!!)
            .orElseThrow { NoSuchElementException("Company with ID ${recommendationInput.companyId} not found") }

        val recommendation = recommendationMapper.recommendationInputToRecommendation(recommendationInput, student, company)
        recommendation.student = student
        recommendation.company = company

        return recommendationMapper.recommendationToRecommendationOutput(
            recommendationRepository.save(recommendation)
        )
    }

    /**
     * Update a Task entity in the database
     * @param recommendationInput the dto input for Task
     * @return the new Task created
     */
    @Throws(NoSuchElementException::class)
    override fun update(recommendationInput: RecommendationInput): RecommendationOutput? {
        val recommendation: Optional<Recommendation> = recommendationRepository.findById(recommendationInput.idRecommendation!!)
        if (recommendation.isEmpty) {
            throw NoSuchElementException(String.format("The recommendation with the id: %s not found!", recommendationInput.idRecommendation))
        }
        val recommendationUpdated: Recommendation = recommendation.get()
        recommendationMapper.recommendationInputToRecommendation(recommendationInput, recommendationUpdated)
        return recommendationMapper.recommendationToRecommendationOutput(recommendationRepository.save(recommendationUpdated))
    }

    /**
     * Delete a Task by id from Database
     * @param id of the Task
     */
    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!recommendationRepository.findById(id).isEmpty) {
            recommendationRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The recommendation with the id: %s not found!", id))
        }
    }
}
