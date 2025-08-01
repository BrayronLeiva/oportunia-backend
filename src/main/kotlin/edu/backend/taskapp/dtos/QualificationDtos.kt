package edu.backend.taskapp.dtos

data class QualificationInput(
    var idQualification: Long? = null,
    var nameQualification: String? = null
)

data class QualificationOutput(
    var idQualification: Long? = null,
    var nameQualification: String?
)
