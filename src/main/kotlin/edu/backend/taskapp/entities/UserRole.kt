package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "user_role")
data class UserRole(
    @EmbeddedId
    val idUserRole: UserRoleId,

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    val role: Role
)