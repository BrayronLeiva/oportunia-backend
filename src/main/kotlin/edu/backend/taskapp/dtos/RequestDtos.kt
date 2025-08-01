package edu.backend.taskapp.dtos

data class RequestInput(
    var idRequest: Long? = null,
    var state: Boolean? = null,
    var studentId: Long? = null,
    var internshipLocationId: Long? = null
)

data class RequestStudentInput(
    var internshipLocationId: Long? = null
)

data class RequestOutput(
    var idRequest: Long,
    var state: Boolean?,
    var student: StudentOutput,
    var internshipLocation: InternshipLocationOutput
)