package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.CertificationInput
import edu.backend.taskapp.dtos.CertificationOutput
import edu.backend.taskapp.entities.Certification
import edu.backend.taskapp.entities.Student
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CertificationMapper {
    fun certificationToCertificationOutput(
        certification: Certification
    ) : CertificationOutput

    fun certificationListToCertificationOutputList(
        certificationList: List<Certification>
    ) : List<CertificationOutput>

    fun certificationInputToCertification (
        certificationInput: CertificationInput
    ) : Certification

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun certificationInputToCertification(
        dto: CertificationInput,
        @MappingTarget certification: Certification
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun certificationInputToCertification(
        certificationInput: CertificationInput,
        student: Student
    ): Certification {
        val certification = certificationInputToCertification(certificationInput)
        return certification.copy(student = student)
    }
}
