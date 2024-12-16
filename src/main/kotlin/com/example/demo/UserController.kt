package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService, private val userRepository: UserRepository) {

    @GetMapping
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @PostMapping
    fun createUser(@RequestBody user: User): User = userRepository.save(user)
}
