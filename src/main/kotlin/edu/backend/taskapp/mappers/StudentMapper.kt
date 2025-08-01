package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.StudentImageOutput
import edu.backend.taskapp.dtos.StudentInput
import edu.backend.taskapp.dtos.StudentOutput
import edu.backend.taskapp.dtos.StudentQualificationsOutput
import edu.backend.taskapp.entities.Qualification
import edu.backend.taskapp.entities.RatingCompanyStudent
import edu.backend.taskapp.entities.Request
import edu.backend.taskapp.entities.Student
import edu.backend.taskapp.entities.User
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StudentMapper {

    fun studentToStudentOutput(
        student: Student
    ): StudentOutput

    fun studentListToStudentOutputList(
        students: List<Student>
    ): List<StudentOutput>

    fun studentInputToStudent(
        input: StudentInput
    ): Student

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun studentInputToStudent(
        dto: StudentInput,
        @MappingTarget entity: Student
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun studentInputToStudent(
        dto: StudentInput,
        user: User,
    ): Student {
        val entity = studentInputToStudent(dto)
        return entity.copy(
            user = user,
        )
    }

    fun studentListToStudentQualificationsOutputList(
        students: List<Student>
    ): List<StudentQualificationsOutput>

    fun studentToStudentImageOutput(
        student: Student
    ): StudentImageOutput


}