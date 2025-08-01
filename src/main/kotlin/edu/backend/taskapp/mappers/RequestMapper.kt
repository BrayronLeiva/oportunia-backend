package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.RequestInput
import edu.backend.taskapp.dtos.RequestOutput
import edu.backend.taskapp.entities.InternshipLocation
import edu.backend.taskapp.entities.Request
import edu.backend.taskapp.entities.Student
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RequestMapper {

    fun requestToRequestOutput(
        request: Request
    ): RequestOutput

    fun requestListToRequestOutputList(
        requests: List<Request>
    ): List<RequestOutput>

    fun requestInputToRequest(
        input: RequestInput
    ): Request

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun requestInputToRequest(
        dto: RequestInput,
        @MappingTarget entity: Request
    )

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun requestInputToRequest(
        dto: RequestInput,
        student: Student,
        internshipLocation: InternshipLocation
    ): Request {
        val entity = requestInputToRequest(dto)
        return entity.copy(
            student = student,
            internshipLocation = internshipLocation
        )
    }
}