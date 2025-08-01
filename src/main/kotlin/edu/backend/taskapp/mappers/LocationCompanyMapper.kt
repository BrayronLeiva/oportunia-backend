package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.LocationCompanyInput
import edu.backend.taskapp.dtos.LocationCompanyOutput
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.LocationCompany
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LocationCompanyMapper {

    fun locationCompanyToLocationCompanyOutput(
        locationCompany: LocationCompany
    ): LocationCompanyOutput

    fun locationCompanyListToLocationCompanyOutputList(
        locationCompanies: List<LocationCompany>
    ): List<LocationCompanyOutput>

    fun locationCompanyInputToLocationCompany(
        input: LocationCompanyInput
    ): LocationCompany

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun locationCompanyInputToLocationCompany(
        dto: LocationCompanyInput,
        @MappingTarget entity: LocationCompany
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun locationCompanyInputToLocationCompany(
        dto: LocationCompanyInput,
        company: Company
    ): LocationCompany {
        val entity = locationCompanyInputToLocationCompany(dto)
        return entity.copy(
            company = company
        )
    }
}