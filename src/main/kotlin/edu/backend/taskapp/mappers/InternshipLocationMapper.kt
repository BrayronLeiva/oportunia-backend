package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.InternshipLocationInput
import edu.backend.taskapp.dtos.InternshipLocationOutput
import edu.backend.taskapp.entities.Internship
import edu.backend.taskapp.entities.InternshipLocation
import edu.backend.taskapp.entities.LocationCompany
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface InternshipLocationMapper {

    fun internshipLocationToInternshipLocationOutput(
        internshipLocation: InternshipLocation
    ): InternshipLocationOutput

    fun internshipLocationListToInternshipLocationOutputList(
        internshipLocations: List<InternshipLocation>
    ): List<InternshipLocationOutput>

    fun internshipLocationInputToInternshipLocation(
        input: InternshipLocationInput
    ): InternshipLocation

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun internshipLocationInputToInternshipLocation(
        dto: InternshipLocationInput,
        @MappingTarget entity: InternshipLocation
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun internshipLocationInputToInternshipLocation(
        dto: InternshipLocationInput,
        locationCompany: LocationCompany,
        internship: Internship
    ): InternshipLocation {
        val entity = internshipLocationInputToInternshipLocation(dto)
        return entity.copy(
            locationCompany = locationCompany,
            internship = internship
        )
    }
}