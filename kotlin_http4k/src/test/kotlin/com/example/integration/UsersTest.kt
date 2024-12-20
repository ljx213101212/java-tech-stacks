package com.example.integration

import com.example.IntegrationTest
import com.example.controller.UserApi
import io.kotest.matchers.shouldBe
import org.http4k.client.Java8HttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UsersTest {

    private val baseUrl = "http://localhost:9000"
    private val client = Java8HttpClient()
    @BeforeEach
    fun setUp() {
        IntegrationTest.app.resetDb()
    }
    @Test
    fun `Get User - returns OK for existing user`() {
        val userApi = UserApi()
        // Given
        val addUserResponse = addUser("John Doe", "john@example.com")

        // Parse the response using the lens to extract a UserResponse object
        val createUserResponse = userApi.userResponseLens(addUserResponse)
        val userId = createUserResponse.id

        // When
        val response = client(Request(Method.GET, "$baseUrl/api/users/$userId"))
        // Then
        response.status shouldBe Status.OK

        val getUserResponse = userApi.userResponseLens(response)
        getUserResponse.name shouldBe "John Doe"
    }

}
