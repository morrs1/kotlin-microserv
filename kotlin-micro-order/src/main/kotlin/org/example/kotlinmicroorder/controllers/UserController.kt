package org.example.kotlinmicroorder.controllers

import lombok.AllArgsConstructor
import org.example.kotlinmicroorder.models.User
import org.example.kotlinmicroorder.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@AllArgsConstructor
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun getAllUsers(): List<User> {
        return userService.findAll()
    }
}