package edu.backend.taskapp.dtos

data class CompanyInput(
    var idCompany: Long? = null,
    var nameCompany: String? = null,
    var description: String? = null,
    var history: String? = null,
    var mision: String? = null,
    var vision: String? = null,
    var corporateCultur: String? = null,
    var contactCompany: Int? = null,
    var ratingCompany: Double? = null,
    var internshipType: String? = null,
    var imageProfile: String? = null,
    var userId: Long? = null,
    var locationCompanies: List<LocationCompanyInput>? = emptyList(),
    var ratingCompanyStudents: List<RatingCompanyStudentInput>? = emptyList(),
    var recommendations: List<RecommendationInput>? = emptyList(),
    var questions: List<QuestionInput>? = emptyList()
)

data class CompanyOutput(
    var idCompany: Long,
    var nameCompany: String,
    var description: String,
    var history: String,
    var mision: String,
    var vision: String,
    var corporateCultur: String,
    var contactCompany: Int,
    var ratingCompany: Double,
    var internshipType: String,
    var imageProfile: String?,
    var user: UserOutput,
    //var locationCompanies: List<LocationCompanyOutput>,
    //var ratingCompanyStudents: List<RatingCompanyStudentOutput>,
    //var recommendations: List<RecommendationOutput>,
    //var questions: List<QuestionOutput>
)

data class CompanyImageOutput(
    var idCompany: Long,
    var nameCompany: String,
    var description: String,
    var history: String,
    var mision: String,
    var vision: String,
    var corporateCultur: String,
    var contactCompany: Int,
    var ratingCompany: Double,
    var internshipType: String,
    var imageProfile: String?,
    var user: UserOutput
)