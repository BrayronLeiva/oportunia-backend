package edu.backend.taskapp.mappers

import edu.backend.taskapp.dtos.UserRoleInput
import edu.backend.taskapp.dtos.UserRoleOutput
import edu.backend.taskapp.entities.Role
import edu.backend.taskapp.entities.User
import edu.backend.taskapp.entities.UserRole
import edu.backend.taskapp.entities.UserRoleId
import org.mapstruct.*
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserRoleMapper {

    fun userRoleToUserRoleOutput(userRole: UserRole): UserRoleOutput

    fun userRoleListToUserRoleOutputList(userRoles: List<UserRole>): List<UserRoleOutput>

    fun userRoleInputToUserRoleId(input: UserRoleInput): UserRoleId {
        return UserRoleId(userId = input.userId, roleId = input.roleId)
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun userRoleInputToUserRole(
        input: UserRoleInput,
        @MappingTarget userRole: UserRole
    )

    fun userRoleInputToUserRole(input: UserRoleInput, user: User, role: Role): UserRole {
        return UserRole(
            idUserRole = UserRoleId(userId = input.userId, roleId = input.roleId),
            user = user,
            role = role
        )
    }
}