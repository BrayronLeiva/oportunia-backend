package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.UserInput
import edu.backend.taskapp.dtos.UserOutput
import edu.backend.taskapp.services.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.users}")
class UserController(private val userService: UserService) {

    @GetMapping
    @ResponseBody
    fun findAll() = userService.findAll()

    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long) = userService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody userInput: UserInput): UserOutput? {
        return userService.create(userInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody userInput: UserInput): UserOutput? {
        return userService.update(userInput)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id: Long) {
        userService.deleteById(id)
    }
}