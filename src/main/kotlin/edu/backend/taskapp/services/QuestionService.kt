package edu.backend.taskapp.services

import edu.backend.taskapp.CompanyRepository
import edu.backend.taskapp.dtos.QuestionInput
import edu.backend.taskapp.dtos.QuestionOutput
import edu.backend.taskapp.mappers.QuestionMapper
import edu.backend.taskapp.QuestionRepository
import edu.backend.taskapp.entities.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

interface QuestionService {
    /**
     * Find all Task
     * @return a list of Users
     */
    fun findAll(): List<QuestionOutput>?

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    fun findById(id: Long): QuestionOutput?

    /**
     * Save and flush a Task entity in the database
     * @param questionInput
     * @return the user created
     */
    fun create(questionInput: QuestionInput): QuestionOutput?

    /**
     * Update a Task entity in the database
     * @param questionInput the dto input for Task
     * @return the new Task created
     */
    fun update(questionInput: QuestionInput): QuestionOutput?

    /**
     * Delete a Task by id from Database
     * @param id of the Task
     */
    fun deleteById(id: Long)
}

@Service
class AbstractQuestionService(
    @Autowired
    val companyRepository: CompanyRepository,
    @Autowired
    val questionRepository: QuestionRepository,
    @Autowired
    val questionMapper: QuestionMapper,

    ) : QuestionService {
    /**
     * Find all Task
     * @return a list of Users
     */
    override fun findAll(): List<QuestionOutput>? {
        return questionMapper.questionListToQuestionOutputList(
            questionRepository.findAll()
        )
    }

    /**
     * Get one Task by id
     * @param id of the Task
     * @return the Task found
     */
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): QuestionOutput? {
        val question: Optional<Question> = questionRepository.findById(id)
        if (question.isEmpty) {
            throw NoSuchElementException(String.format("The question with the id: %s not found!", id))
        }
        return questionMapper.questionToQuestionOutput(
            question.get(),
        )
    }

    /**
     * Save and flush a Task entity in the database
     * @param questionInput
     * @return the user created
     */
    override fun create(questionInput: QuestionInput): QuestionOutput? {
        val company = companyRepository.findById(questionInput.companyId!!)
            .orElseThrow { NoSuchElementException("Company with ID ${questionInput.companyId} not found") }

        val question = questionMapper.questionInputToQuestion(questionInput, company)
        question.company = company

        return questionMapper.questionToQuestionOutput(
            questionRepository.save(question)
        )
    }

    /**
     * Update a Task entity in the database
     * @param questionInput the dto input for Task
     * @return the new Task created
     */
    @Throws(NoSuchElementException::class)
    override fun update(questionInput: QuestionInput): QuestionOutput? {
        val question: Optional<Question> = questionRepository.findById(questionInput.idQuestion!!)
        if (question.isEmpty) {
            throw NoSuchElementException(String.format("The question with the id: %s not found!", questionInput.idQuestion))
        }
        val questionUpdated: Question = question.get()
        questionMapper.questionInputToQuestion(questionInput, questionUpdated)
        return questionMapper.questionToQuestionOutput(questionRepository.save(questionUpdated))
    }

    /**
     * Delete a Task by id from Database
     * @param id of the Task
     */
    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if (!questionRepository.findById(id).isEmpty) {
            questionRepository.deleteById(id)
        } else {
            throw NoSuchElementException(String.format("The question with the id: %s not found!", id))
        }
    }
}
