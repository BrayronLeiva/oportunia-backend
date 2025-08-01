package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.UserInput
import edu.backend.taskapp.dtos.UserOutput
import edu.backend.taskapp.entities.User
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {

    fun userToUserOutput(
        entity: User
    ): UserOutput

    fun userListToUserOutputList(
        entities: List<User>
    ): List<UserOutput>

    fun userInputToUser(
        input: UserInput
    ): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun userInputToUser(
        input: UserInput,
        @MappingTarget entity: User
    )
    fun userOutputToUserInput(
        output: UserOutput
    ): UserInput


    // Optional if you use UserResult for login responses
    //fun userToUserResult(
    //    entity: User
    //): UserResult
}