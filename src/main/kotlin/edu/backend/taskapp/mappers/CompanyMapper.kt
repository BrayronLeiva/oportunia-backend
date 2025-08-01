package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.CompanyImageOutput
import edu.backend.taskapp.dtos.CompanyInput
import edu.backend.taskapp.dtos.CompanyOutput
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.User
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CompanyMapper {

    fun companyToCompanyOutput(
        company: Company
    ): CompanyOutput

    fun companyListToCompanyOutputList(
        companies: List<Company>
    ): List<CompanyOutput>

    fun companyInputToCompany(
        companyInput: CompanyInput
    ): Company

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun companyInputToCompany(
        dto: CompanyInput,
        @MappingTarget company: Company
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun companyInputToCompany(
        companyInput: CompanyInput,
        user: User
    ): Company {
        val company = companyInputToCompany(companyInput)
        return company.copy(user = user)
    }


    fun companyToCompanyImageOutput(
        company: Company
    ): CompanyImageOutput
}