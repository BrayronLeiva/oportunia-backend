package edu.backend.taskapp.webservices

import edu.backend.taskapp.dtos.UserRoleInput
import edu.backend.taskapp.dtos.UserRoleOutput
import edu.backend.taskapp.entities.UserRoleId
import edu.backend.taskapp.services.UserRoleService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.user-roles}")
class UserRoleController(private val userRoleService: UserRoleService) {
    @GetMapping
    @ResponseBody
    fun findAll() = userRoleService.findAll()

    @GetMapping("find")
    @ResponseBody
    fun find(@RequestBody input: UserRoleId) = userRoleService.find(input)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody userInput: UserRoleInput): UserRoleOutput? {
        return userRoleService.create(userInput)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody userInput: UserRoleInput): UserRoleOutput? {
        return userRoleService.update(userInput)
    }

    @DeleteMapping("delete")
    @ResponseBody
    fun deleteById(@RequestBody input: UserRoleId) {
        userRoleService.delete(input)
    }
}