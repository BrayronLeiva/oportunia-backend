package edu.backend.taskapp.dtos

data class StudentQualificationInput(
    val studentId: Long,
    val qualificationId: Long
)

data class StudentQualificationOutput(
    val student: StudentOutput,
    val qualification: QualificationOutput
)