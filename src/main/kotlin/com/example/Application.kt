package com.example

import io.ktor.application.*
import com.example.plugins.*
import io.ktor.features.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database
import java.sql.*
import javax.sql.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureRouting()
    configureSerialization()
    configureMonitoring()
    Database.connect("jdbc:sqlite:./src/main/kotlin/com/example/data.db", "org.sqlite.JDBC")
    println("starting")
}
