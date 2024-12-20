package com.example

import com.example.controller.UserApi
import initDatabase
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer


val userApi = UserApi()
val app: HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },
    "/api/users" bind Method.POST to userApi.createUser(),
    "/api/users/{id}" bind GET to userApi.getUser()
)

fun startApp(): Http4kServer {
    val printingApp: HttpHandler = DebuggingFilters.PrintRequestAndResponse().then(app)
    val server = printingApp.asServer(SunHttp(9000)).start()
    return server;
}

fun main() {
    initDatabase()
    val server = startApp()
    println("Server started on " + server.port())
}

