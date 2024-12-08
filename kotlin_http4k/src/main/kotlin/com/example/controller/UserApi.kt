package com.example.controller

import com.example.repository.UserRepository
import org.http4k.core.*
import org.http4k.core.Method.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.path

data class CreateUserRequest(val name: String, val email: String)
data class UserResponse(val id: Int, val name: String, val email: String)

class UserApi {
    val createUserLens = Body.auto<CreateUserRequest>().toLens()
    val userResponseLens = Body.auto<UserResponse>().toLens()

    fun createUser(): HttpHandler = { req ->
        val request = createUserLens(req)
        val user = UserRepository.addUser(request.name, request.email)
        Response(Status.CREATED).with(userResponseLens of UserResponse(user.id, user.name, user.email))
    }

    fun getUser(): HttpHandler = { req ->
        run {
            val userId = req.path("id")?.toIntOrNull()
                ?: return@run Response(Status.BAD_REQUEST).body("Invalid user ID")

            val user = UserRepository.getUserById(userId)
                ?: return@run Response(Status.NOT_FOUND).body("User not found")

            Response(Status.OK).with(userResponseLens of UserResponse(user.id, user.name, user.email))
        }
    }


}