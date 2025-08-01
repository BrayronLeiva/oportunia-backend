package edu.backend.taskapp.dtos

data class LocationCompanyInput(
    var idLocationCompany: Long? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var email: String? = null,
    var contactLocation: Int? = null,
    var companyId: Long? = null,
    var internshipLocations: List<InternshipLocationInput>? = emptyList()
)

data class LocationCompanyOutput(
    var idLocationCompany: Long,
    var latitude: Double,
    var longitude: Double,
    var email: String,
    var contactLocation: Int,
    var company: CompanyOutput,
    //var internshipLocations: List<InternshipLocationOutput>
)



data class LocationRequestDTO(
    val latitude: Double,
    val longitude: Double
)
