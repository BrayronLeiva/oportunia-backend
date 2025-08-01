package edu.backend.taskapp.dtos

data class RecommendationInput (
    var idRecommendation: Long? = null,
    var details: String? = null,
    var studentId: Long? = null,
    var companyId: Long? = null
)

data class RecommendationOutput (
    var idRecommendation: Long,
    var details: String,
    var student: StudentOutput,
    var company: CompanyOutput
)