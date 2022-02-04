package com.example.routes

import com.example.controllers.EmployeeController
import com.example.models.Employee
import com.example.models.employees_list
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.employeeRouting(){
    val employeeController = EmployeeController()
    route("/employee"){
        get{
            call.respond(employeeController.getAllEmployees())
        }

        get("/{id}"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            call.respond(employeeController.getEmployeeById(id))
        }

        post{
            val employee: Employee = call.receive()
            employeeController.addEmployee(employee)
            call.respondText("Employee added successfully", status=HttpStatusCode.Created)
        }

        put{
            val new_employee:Employee = call.receive()
            employeeController.updateEmployee(new_employee)
            call.respondText("Put success", status=HttpStatusCode.OK)
        }

        delete("/{id}"){
            val id = call.parameters["id"] ?: return@delete call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            employeeController.deleteEmployeeById(id)
            call.respondText("Deleted", status = HttpStatusCode.OK)
        }
    }
}