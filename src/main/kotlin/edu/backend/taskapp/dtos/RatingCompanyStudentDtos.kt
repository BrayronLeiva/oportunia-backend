package edu.backend.taskapp.dtos

data class RatingCompanyStudentInput(
    var idRating: Long? = null,
    var rating: Double? = null,
    var type: String? = null,
    var comment: String? = null,
    var studentId: Long? = null,
    var companyId: Long? = null
)

data class RatingCompanyStudentOutput(
    var idRating: Long,
    var rating: Double,
    var type: String,
    var comment: String,
    var student: StudentOutput,
    var company: CompanyOutput
)