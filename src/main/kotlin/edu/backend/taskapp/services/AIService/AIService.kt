package edu.backend.taskapp.services.AIService

import edu.backend.taskapp.dtos.CompanyOutput
import edu.backend.taskapp.dtos.InternshipEvaluateOutput
import edu.backend.taskapp.dtos.InternshipLocationMatchOutput
import edu.backend.taskapp.dtos.InternshipLocationOutput
import edu.backend.taskapp.dtos.InternshipMatchResult
import edu.backend.taskapp.dtos.InternshipOutput
import edu.backend.taskapp.dtos.StudentMatchResult
import edu.backend.taskapp.dtos.StudentOutput
import edu.backend.taskapp.services.AIService.AIStrategies.AIConnectionStrategy
import org.springframework.stereotype.Service



interface AIService {
    fun matchStudentsWithCompany(
        company: CompanyOutput,
        students: List<StudentOutput>
    ): List<StudentMatchResult>

    fun recommendInternshipsForStudent(
        student: StudentOutput,
        nearbyInternships: List<InternshipLocationOutput>
    ): List<InternshipLocationMatchOutput>
}

@Service
class AbstractAIService(
    private val aiStrategy: AIConnectionStrategy
) : AIService {

    override fun matchStudentsWithCompany(
        company: CompanyOutput,
        students: List<StudentOutput>
    ): List<StudentMatchResult> {
        return students.mapNotNull { student ->

            val prompt = generatePrompt(student, company)
            val behaviour = "\"Eres un sistema experto en selección de pasantías.\""
            aiStrategy.make_request(prompt, behaviour)?.let { content ->
                parseResponse(content, student)
            }
        }
    }


    private fun generatePrompt(student: StudentOutput, company: CompanyOutput): String {
        return """
        Eres un sistema experto en selección de pasantes. Evalúa si el siguiente estudiante es adecuado para hacer una pasantía en la empresa descrita.

        Devuelve una puntuación de 0 a 100 y explica brevemente el motivo.

        Estudiante:
        Nombre: ${student.nameStudent}
        Información personal: ${student.personalInfo}
        Experiencia: ${student.experience}
        Rating: ${student.ratingStudent}

        Empresa:
        Nombre: ${company.nameCompany}
        Descripción: ${company.description}
        Misión: ${company.mision}
        Tipo de pasantía: ${company.internshipType}
        Cultura corporativa: ${company.corporateCultur}
        Rating: ${company.ratingCompany}

        Formato de respuesta:
        score: [número del 0 al 100]
        reason: [explicación breve]
    """.trimIndent()
    }



    private fun parseResponse(content: String, student: StudentOutput): StudentMatchResult {
        val scoreRegex = Regex("score:\\s*(\\d+)")
        val reasonRegex = Regex("reason:\\s*(.*)", RegexOption.DOT_MATCHES_ALL)

        val score = scoreRegex.find(content)?.groupValues?.get(1)?.toIntOrNull() ?: 0
        val reason = reasonRegex.find(content)?.groupValues?.get(1)?.trim() ?: "No se pudo obtener explicación"

        return StudentMatchResult(
            idStudent = student.idStudent,
            nameStudent = student.nameStudent,
            identification = student.identification,
            personalInfo = student.personalInfo,
            experience = student.experience,
            ratingStudent = student.ratingStudent,
            user = student.user,
            reason = reason,
            imageProfile = student.imageProfile,
            homeLatitude = student.homeLatitude,
            homeLongitude = student.homeLongitude,
            score = score,
        )
    }

    override fun recommendInternshipsForStudent(
        student: StudentOutput,
        nearbyInternships: List<InternshipLocationOutput>
    ): List<InternshipLocationMatchOutput> {

        return nearbyInternships.mapNotNull { internshipLocation ->

            val company = internshipLocation.locationCompany.company
            val internship = internshipLocation.internship
            val prompt = generatePrompt(student, company, internship)
            val behaviour = "\"Eres un sistema experto en selección de pasantías.\""
            aiStrategy.make_request(prompt, behaviour)?.let { content ->
                parseResponseForInternship(content, internshipLocation)
            }
        }
    }


    private fun generatePrompt(student: StudentOutput, company: CompanyOutput, internship: InternshipOutput): String {
        return """
        Eres un sistema experto en orientación de pasantías. Evalúa si la siguiente pasantía es adecuada para el estudiante descrito, basándote en su experiencia, intereses y habilidades.

        Devuelve una puntuación de 0 a 100 y explica por qué esta pasantía sería beneficiosa para el estudiante.(Hablale al estudiante por su nombre de forma natural)

        Estudiante:
        Nombre: ${student.nameStudent}
        Información personal: ${student.personalInfo}
        Experiencia: ${student.experience}
        Rating: ${student.ratingStudent}

        Empresa:
        Nombre: ${company.nameCompany}
        Descripción: ${company.description}
        Cultura corporativa: ${company.corporateCultur}

        Pasantía:
        Título: ${internship.details}

        Formato de respuesta:
        score: [0 al 100]
        reason: [explicación]
    """.trimIndent()
    }

    private fun parseResponseForInternship(content: String, internshipLocation: InternshipLocationOutput): InternshipLocationMatchOutput {
        val scoreRegex = Regex("score:\\s*(\\d+)")
        val reasonRegex = Regex("reason:\\s*(.*)", RegexOption.DOT_MATCHES_ALL)

        val score = scoreRegex.find(content)?.groupValues?.get(1)?.toIntOrNull() ?: 0
        val reason = reasonRegex.find(content)?.groupValues?.get(1)?.trim() ?: "No se pudo obtener explicación"

        return InternshipLocationMatchOutput(
            idInternshipLocation = internshipLocation.idInternshipLocation,
            locationCompany = internshipLocation.locationCompany,
            internship = internshipLocation.internship,
            score = score,
            reason = reason
        )
    }




}