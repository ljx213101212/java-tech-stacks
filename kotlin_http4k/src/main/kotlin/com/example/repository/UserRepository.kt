package com.example.repository

import com.example.model.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

data class User(val id: Int, val name: String, val email: String)

object UserRepository {
    private val logger = LoggerFactory.getLogger("com.example.repository.UserRepository")

    fun addUser(name: String, email: String): User {
        logger.debug("Adding user with name=$name and email=$email")

        val id = transaction {
            Users.insertAndGetId {
                it[Users.name] = name
                it[Users.email] = email
            }.value
        }
        return getUserById(id) ?: throw IllegalStateException("User not found after insert")
    }

    fun getUserById(id: Int): User? {
        logger.debug("Fetching user by ID=$id")
        val user = transaction {
            Users.select { Users.id eq id }
                .map { User(it[Users.id].value, it[Users.name], it[Users.email]) }
                .singleOrNull()
        }

        if (user == null) {
            logger.warn("User with ID=$id not found")
        } else {
            logger.debug("User found: $user")
        }

        return user
    }
}
