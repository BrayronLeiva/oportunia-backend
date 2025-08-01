package edu.backend.taskapp.services

import edu.backend.taskapp.RoleRepository
import edu.backend.taskapp.UserRepository
import edu.backend.taskapp.UserRoleRepository
import edu.backend.taskapp.dtos.UserRoleInput
import edu.backend.taskapp.dtos.UserRoleOutput
import edu.backend.taskapp.entities.UserRole
import edu.backend.taskapp.entities.UserRoleId
import edu.backend.taskapp.mappers.UserRoleMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserRoleService {
    fun findAll(): List<UserRoleOutput>?
    fun find(input: UserRoleId): UserRoleOutput?
    fun create(input: UserRoleInput): UserRoleOutput?
    fun update(input: UserRoleInput): UserRoleOutput?
    fun delete(input: UserRoleId)
}

@Service
class AbstractUserRoleService(
    @Autowired
    val userRoleRepository: UserRoleRepository,
    @Autowired
    val userRoleMapper: UserRoleMapper,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val roleRepository: RoleRepository
) : UserRoleService {

    override fun findAll(): List<UserRoleOutput>? {
        return userRoleMapper.userRoleListToUserRoleOutputList(
            userRoleRepository.findAll()
        )
    }

    override fun find(input: UserRoleId): UserRoleOutput? {
        val userRole = userRoleRepository.findById(input)
        if (userRole.isEmpty) {
            throw NoSuchElementException("$input not found")
        }
        return userRoleMapper.userRoleToUserRoleOutput(userRole.get())
    }

    override fun create(input: UserRoleInput): UserRoleOutput? {
        val userRoleId = userRoleMapper.userRoleInputToUserRoleId(input)
        val user = userRepository.findById(input.userId)
        val role = roleRepository.findById(input.roleId)
        if (user.isEmpty || role.isEmpty) {
            throw NoSuchElementException("User or Role not found")
        }
        val userRole = UserRole(idUserRole = userRoleId, user = user.get(), role = role.get())
        val saved = userRoleRepository.save(userRole)
        return userRoleMapper.userRoleToUserRoleOutput(saved)
    }

    override fun update(input: UserRoleInput): UserRoleOutput? {
        val id = userRoleMapper.userRoleInputToUserRoleId(input)
        val existing = userRoleRepository.findById(id)
        if (existing.isEmpty) {
            throw NoSuchElementException("$input not found")
        }
        val userRole = existing.get()
        userRoleMapper.userRoleInputToUserRole(input, userRole)
        return userRoleMapper.userRoleToUserRoleOutput(
            userRoleRepository.save(userRole)
        )
    }

    override fun delete(input: UserRoleId) {
        if (userRoleRepository.existsById(input)) {
            userRoleRepository.deleteById(input)
        } else {
            throw NoSuchElementException("$input not found")
        }
    }
}