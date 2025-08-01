package edu.backend.taskapp.dtos

import java.util.Date

data class PrivilegeDetails (
    var id: Long? = null,
    var name: String? = null
)

data class RoleDetails (
    var id: Long? = null,
    var name: String? = null,
    var privileges: List<PrivilegeDetails>? = emptyList(),
)

data class UserLoginInput(
    var username: String = "",
    var password: String = "",
)

data class UserInput(
    var id: Long?=null,
    var firstName: String?=null,
    var lastName: String?=null,
    var email: String?=null,
    var password: String?=null,
    var enabled: Boolean?=null,
    var tokenExpired: Boolean?=null,
    var createDate: Date?= Date(),
    var roleList: Set<RoleDetails>?=emptySet(),
)

data class UserOutput(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var enabled: Boolean?,
    var tokenExpired: Boolean?,
    var createDate: Date,
    var roleList: Set<RoleDetails>?,
)