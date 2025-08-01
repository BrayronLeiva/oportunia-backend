package edu.backend.taskapp.dtos

data class InternshipInput(
    var idInternship: Long? = null,
    var details: String? = null,
    var internshipLocations: List<InternshipLocationInput>? = emptyList()
)

data class InternshipOutput(
    var idInternship: Long?,
    var details: String?,
    //var internshipLocations: List<InternshipLocationOutput>
)

data class InternshipEvaluateOutput(
    var idInternship: Long?,
    var details: String?,
    var company : CompanyOutput
    //var internshipLocations: List<InternshipLocationOutput>
)

data class InternshipMatchResult(
    val internshipId: Long,
    val internshipTitle: String,
    val score: Int,
    val reason: String
    
)
