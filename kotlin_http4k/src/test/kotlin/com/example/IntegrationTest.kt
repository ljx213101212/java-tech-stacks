package com.example

import com.example.model.Users
import org.http4k.server.Http4kServer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils
import java.io.Closeable
import io.kotest.core.listeners.ProjectListener
import org.jetbrains.exposed.sql.Table


class App : Closeable {
    val db: Database
    private val server: Http4kServer

    init {
        server = startApp()
        db = Database.connect("jdbc:h2:mem:kotlin_demo_testdb;DB_CLOSE_DELAY=-1",  "org.h2.Driver")

        // Create the user table
        transaction(db) {
            SchemaUtils.create(Users)
        }
    }

    fun resetDb() {
        transaction(db) {
            val tables = getAllTables()
            if (tables.isNotEmpty()) {
                val sql = buildString {
                    append("SET REFERENTIAL_INTEGRITY FALSE; ")
                    tables.forEach { tableName -> append("TRUNCATE TABLE $tableName; ") }
                    append("SET REFERENTIAL_INTEGRITY TRUE;")
                }
                exec(sql)
            }
        }
    }

    private fun getAllTables(): MutableList<String> {
        val statement = db.connector().prepareStatement("SHOW TABLES", false);
        val sqlResult = statement.executeQuery();
        val result = mutableListOf<String>()

        while (sqlResult.next()) {
            result.add(sqlResult.getString(1))
        }
        return result
    }

    override fun close() {
        db.connector().close()
        server.stop()
    }
}

object IntegrationTest : ProjectListener {
    private val lazyApp = lazy { App() }
    val app: App by lazyApp

    override suspend fun afterProject() {
        if (lazyApp.isInitialized()) {
            app.close()
        }
    }
}
