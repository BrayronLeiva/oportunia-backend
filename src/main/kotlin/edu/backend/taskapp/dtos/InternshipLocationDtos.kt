package edu.backend.taskapp.dtos

data class InternshipLocationInput(
    var idInternshipLocation: Long? = null,
    var locationCompanyId: Long? = null,
    var internshipId: Long? = null,
    var requests: List<RequestInput>? = emptyList()
)

data class InternshipLocationOutput(
    var idInternshipLocation: Long,
    var locationCompany: LocationCompanyOutput,
    var internship: InternshipOutput
)

data class InternshipLocationFlagOutput(
    var idInternshipLocation: Long,
    var locationCompany: LocationCompanyOutput,
    var internship: InternshipOutput,
    val requested: Boolean
    
)

data class InternshipLocationMatchOutput(
    var idInternshipLocation: Long,
    var locationCompany: LocationCompanyOutput,
    var internship: InternshipOutput,
    val score: Int,
    val reason: String
    //var requests: List<RequestOutput>
)
data class InternshipLocationMatchFlagOutput(
    var idInternshipLocation: Long,
    var locationCompany: LocationCompanyOutput,
    var internship: InternshipOutput,
    val score: Int,
    val reason: String,
    val requested: Boolean
    //var requests: List<RequestOutput>
)