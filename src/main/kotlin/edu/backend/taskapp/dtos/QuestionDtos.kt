package edu.backend.taskapp.dtos

data class QuestionInput(
    var idQuestion: Long? = null,
    var question: String? = null,
    var answer: String? = null,
    var companyId: Long? = null
)

data class QuestionOutput(
    var idQuestion: Long,
    var question: String,
    var answer: String,
    var company : CompanyOutput
)