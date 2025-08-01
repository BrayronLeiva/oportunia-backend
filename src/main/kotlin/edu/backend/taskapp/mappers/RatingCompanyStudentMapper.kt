package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.RatingCompanyStudentInput
import edu.backend.taskapp.dtos.RatingCompanyStudentOutput
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.RatingCompanyStudent
import edu.backend.taskapp.entities.Student
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RatingCompanyStudentMapper {

    fun ratingCompanyStudentToRatingCompanyStudentOutput(
        ratingCompanyStudent: RatingCompanyStudent
    ): RatingCompanyStudentOutput

    fun ratingCompanyStudentListToRatingCompanyStudentOutputList(
        ratingCompanyStudents: List<RatingCompanyStudent>
    ): List<RatingCompanyStudentOutput>

    fun ratingCompanyStudentInputToRatingCompanyStudent(
        input: RatingCompanyStudentInput
    ): RatingCompanyStudent

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun ratingCompanyStudentInputToRatingCompanyStudent(
        dto: RatingCompanyStudentInput,
        @MappingTarget entity: RatingCompanyStudent
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun ratingCompanyStudentInputToRatingCompanyStudent(
        dto: RatingCompanyStudentInput,
        student: Student,
        company: Company
    ): RatingCompanyStudent {
        val entity = ratingCompanyStudentInputToRatingCompanyStudent(dto)
        return entity.copy(
            student = student,
            company = company
        )
    }
}