package com.example

import com.example.controller.UserApi
import org.http4k.core.*
import org.http4k.core.Method.*
import org.http4k.format.Jackson.auto
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.and
import initDatabase

class RoutesTest {

    private lateinit var app: HttpHandler


    @BeforeEach
    fun setUp() {
        // Initialize the app
        app = routes(
            "/ping" bind GET to { Response(Status.OK).body("pong") },
        )
    }

    @Test
    fun `ping endpoint returns pong`() {
        val response = app(Request(GET, "/ping"))
        assertThat(response, hasStatus(Status.OK) and hasBody("pong"))
    }
}
