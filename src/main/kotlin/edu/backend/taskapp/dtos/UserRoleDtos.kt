package edu.backend.taskapp.dtos

data class UserRoleInput(
    val roleId: Long,
    val userId: Long
)

data class UserRoleOutput(
    val role: RoleDetails,
    val user: UserOutput
)