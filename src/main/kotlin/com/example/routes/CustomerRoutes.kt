package com.example.routes

import com.example.controllers.CustomerController
import com.example.models.Customer
import com.example.models.customers_list
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.text.get

fun Route.customerRouting() {
    val customerController = CustomerController()
    route("/customers") {
        get {
            call.respond(customerController.getAllCustomers())
        }

        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            call.respond(customerController.getCustomerById(id))
        }
        post {
            val customer = call.receive<Customer>()
            customerController.addCustomer(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            customerController.deleteCustomerById(id)
            call.respondText("customer deleted", status = HttpStatusCode.OK)
        }
        get("{id}/change_password/{new_password}"){
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val newPassword = call.parameters["new_password"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            customerController.changePasswordById(id,newPassword)
            call.respondText("password was changed", status = HttpStatusCode.OK)
        }
    }
}