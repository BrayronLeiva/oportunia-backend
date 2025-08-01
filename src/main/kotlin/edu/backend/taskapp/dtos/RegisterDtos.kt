package edu.backend.taskapp.dtos

import java.util.Date

data class RegisterStudentInput(
    var id: Long?=null,
    var firstName: String?=null,
    var lastName: String?=null,
    var email: String?=null,
    var password: String?=null,
    var enabled: Boolean?=null,
    var tokenExpired: Boolean?=null,
    var createDate: Date?= Date(),
    var roleList: Set<RoleDetails>?=emptySet(),

    var idStudent: Long? = null,
    var nameStudent: String? = null,
    var identification: String? = null,
    var personalInfo: String? = null,
    var experience: String? = null,
    var ratingStudent: Double? = null,
    var userId: Long? = null,
    var imageProfile: String? = null,
    var homeLatitude: Double? = null,
    var homeLongitude: Double? = null,
    var qualifications: List<QualificationInput>? = emptyList(),
    var requests: List<RequestInput>? = emptyList(),
    var ratings: List<RatingCompanyStudentInput>? = emptyList(),
)

data class RegisterStudentOutput (
    var user: UserOutput,
    var student: StudentOutput
)

data class RegisterCompanyInput(
    var id: Long?=null,
    var firstName: String?=null,
    var lastName: String?=null,
    var email: String?=null,
    var password: String?=null,
    var enabled: Boolean?=null,
    var tokenExpired: Boolean?=null,
    var createDate: Date?= Date(),
    var roleList: Set<RoleDetails>?=emptySet(),

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

data class RegisterCompanyOutput(
    var user: UserOutput,
    var company: CompanyOutput
)