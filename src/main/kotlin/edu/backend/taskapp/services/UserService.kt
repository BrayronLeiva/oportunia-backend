package edu.backend.taskapp.services

import edu.backend.taskapp.dtos.UserInput
import edu.backend.taskapp.dtos.UserOutput
import edu.backend.taskapp.mappers.UserMapper
import edu.backend.taskapp.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

interface UserService {
    fun findAll(): List<UserOutput>?
    fun findById(id: Long): UserOutput?
    fun findByEmail(email: String): UserOutput?
    fun create(userInput: UserInput): UserOutput?
    fun update(userInput: UserInput): UserOutput?
    fun deleteById(id: Long)
    fun convertUserOuputToUserInput(userOutput: UserOutput?): UserInput?
}

@Service
class AbstractUserService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper,
    val passwordEncoder: BCryptPasswordEncoder
) : UserService {

    override fun findAll(): List<UserOutput>? {
        return userMapper.userListToUserOutputList(
            userRepository.findAll()
        )
    }

    override fun findById(id: Long): UserOutput? {
        val user = userRepository.findById(id)
        if (user.isEmpty) {
            throw NoSuchElementException("The user with the id: $id not found!")
        }
        return userMapper.userToUserOutput(user.get())
    }

    override fun findByEmail(email: String): UserOutput? {
        val user = userRepository.findByEmail(email)
        if (user.isEmpty) {
            throw NoSuchElementException("The user with the id: $email not found!")
        }
        return userMapper.userToUserOutput(user.get())
    }

    override fun create(userInput: UserInput): UserOutput? {

        val user = userMapper.userInputToUser(userInput)

        if (userRepository.findByEmail(user.email).isPresent){
            throw Exception("The user with the id: ${userInput.email} already exists!")
        }

        user.password = passwordEncoder.encode(userInput.password)
        return userMapper.userToUserOutput(
            userRepository.save(user)
        )
    }

    override fun update(userInput: UserInput): UserOutput? {
        val existingUser = userRepository.findById(userInput.id!!)
            .orElseThrow { NoSuchElementException("The user with the id: ${userInput.id} not found!") }

        userMapper.userInputToUser(userInput, existingUser)

        if (!passwordEncoder.matches(userInput.password, existingUser.password)) {
            existingUser.password = passwordEncoder.encode(userInput.password)
        }

        return userMapper.userToUserOutput(
            userRepository.save(existingUser)
        )
    }

    override fun deleteById(id: Long) {
        if (!userRepository.findById(id).isEmpty) {
            userRepository.deleteById(id)
        } else {
            throw NoSuchElementException("The user with the id: $id not found!")
        }
    }

    override fun convertUserOuputToUserInput(userOutput: UserOutput?): UserInput? {
        return userMapper.userOutputToUserInput(userOutput!!)
    }
}