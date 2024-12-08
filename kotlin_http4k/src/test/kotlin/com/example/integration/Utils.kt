package com.example.integration

import io.kotest.matchers.shouldBe
import org.http4k.client.Java8HttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.intellij.lang.annotations.Language


fun addUser(name: String, email: String): Response {
    @Language("JSON")
    val registerReqBody = """
                {
                  "name": "$name",
                  "email": "$email"
                }
            """.trimIndent()
    val send = Java8HttpClient()
    val resp = send(
        Request(
            Method.POST,
            "http://localhost:9000/api/users"
        ).body(registerReqBody)
    )
    resp.status.shouldBe(Status.CREATED)
    return resp
}