package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.RecommendationInput
import edu.backend.taskapp.dtos.RecommendationOutput
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.Recommendation
import edu.backend.taskapp.entities.Student
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RecommendationMapper {

    fun recommendationToRecommendationOutput(
        recommendation: Recommendation
    ): RecommendationOutput

    fun recommendationListToRecommendationOutputList(
        recommendations: List<Recommendation>
    ): List<RecommendationOutput>

    fun recommendationInputToRecommendation(
        input: RecommendationInput
    ): Recommendation

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun recommendationInputToRecommendation(
        dto: RecommendationInput,
        @MappingTarget entity: Recommendation
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun recommendationInputToRecommendation(
        dto: RecommendationInput,
        student: Student,
        company: Company
    ): Recommendation {
        val entity = recommendationInputToRecommendation(dto)
        return entity.copy(
            student = student,
            company = company
        )
    }
}