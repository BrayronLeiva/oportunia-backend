package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.QuestionInput
import edu.backend.taskapp.dtos.QuestionOutput
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.Question
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface QuestionMapper {

    fun questionToQuestionOutput(
        question: Question
    ): QuestionOutput

    fun questionListToQuestionOutputList(
        questions: List<Question>
    ): List<QuestionOutput>

    fun questionInputToQuestion(
        input: QuestionInput
    ): Question

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun questionInputToQuestion(
        dto: QuestionInput,
        @MappingTarget entity: Question
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun questionInputToQuestion(
        dto: QuestionInput,
        company: Company
    ): Question {
        val entity = questionInputToQuestion(dto)
        return entity.copy(
            company = company
        )
    }
}