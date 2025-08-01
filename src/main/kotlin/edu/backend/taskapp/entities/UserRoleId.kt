package edu.backend.taskapp.entities

import java.io.Serializable
import jakarta.persistence.*

@Embeddable
data class UserRoleId(
    @Column(name = "user_id")
    val userId: Long = 0,

    @Column(name = "role_id")
    val roleId: Long = 0
) : Serializable