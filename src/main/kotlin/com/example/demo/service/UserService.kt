package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow {
            RuntimeException("User with id $id not found")
        }
    }

    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun updateUser(id: Long, updatedUser: User): User {
        val existingUser = getUserById(id)
        val newUser = existingUser.copy(
            name = updatedUser.name,
            email = updatedUser.email
        )
        return userRepository.save(newUser)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
