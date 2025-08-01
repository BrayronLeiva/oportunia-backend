package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.InternshipInput
import edu.backend.taskapp.dtos.InternshipOutput
import edu.backend.taskapp.entities.Internship
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface InternshipMapper {

    fun internshipToInternshipOutput(
        internship: Internship
    ): InternshipOutput

    fun internshipListToInternshipOutputList(
        internships: List<Internship>
    ): List<InternshipOutput>

    fun internshipInputToInternship(
        input: InternshipInput
    ): Internship

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun internshipInputToInternship(
        input: InternshipInput,
        @MappingTarget entity: Internship
    )
}