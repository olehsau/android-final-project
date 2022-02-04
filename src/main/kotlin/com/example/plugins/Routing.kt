package com.example.plugins

import com.example.models.Customer
import com.example.models.LoginResponse
import com.example.models.customers_list
import com.example.modelsdb.CustomerDB
import com.example.routes.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    
    routing {
        customerRouting()
        employeeRouting()
        productRouting()
        cartRouting()
        branchRouting()
        couponRouting()
        loginRouting()
        idIsUniqueRouting()
        // Static plugin. Try to access `/static/index.html`
        static{
            resources("static")
        }
    }
}

fun Route.loginRouting() {
    route("/login"){
        get("/{id}/{password}"){
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val password = call.parameters["password"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val customer:ArrayList<Customer> = ArrayList()
            transaction {
                CustomerDB.select(CustomerDB.CustomerId eq id).map{
                    customer.add(Customer(it[CustomerDB.CustomerId],it[CustomerDB.Name],it[CustomerDB.Surname],it[CustomerDB.Email],it[CustomerDB.Password]))
                }
            }
            if(customer.isNotEmpty() && customer[0].password==password){
                call.respond(LoginResponse("good"))
            }
            else call.respond(LoginResponse("bad"))
        }
    }
}

fun Route.idIsUniqueRouting(){
    route("/id_is_unique"){
        get("/{id}"){
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            var customerList:List<ResultRow> = listOf()
            transaction {
                customerList = CustomerDB.select(CustomerDB.CustomerId eq id).toList()
            }
            if(customerList.isEmpty()) call.respond(LoginResponse("good"))
            else call.respond(LoginResponse("bad"))
        }
    }
}