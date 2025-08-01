package edu.backend.taskapp.dtos

data class CertificationInput(
    var idCertification: Long? = null,
    var nameCertification: String? = null,
    var provider: String? = null,
    var studentId: Long? = null,
    var filePath: String? = ""
)
data class CertificationOutput(
    var idCertification: Long,
    var nameCertification: String,
    var provider: String,
    var student: StudentOutput,
    var filePath: String
)