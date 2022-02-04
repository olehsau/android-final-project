package com.example.routes

import com.example.controllers.BranchController
import com.example.models.Branch
import com.example.models.branches_list
import com.example.modelsdb.BranchDB
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.branchRouting(){
    val branchController = BranchController()

    route("/branch"){
        get{
            branchController.getAllBranches()
        }
        get("/{id}"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            call.respond(branchController.getBranchById(id))
        }
        post{
            val new_branch = call.receive<Branch>()
            branchController.addBranch(new_branch)
            call.respondText("successfully added.", status = HttpStatusCode.OK)
        }
        delete("/{id}"){
            val id = call.parameters["id"] ?: return@delete call.respondText("wrong id input", status=HttpStatusCode.NotFound)
            branchController.deleteBranchById(id)
            call.respondText("Deleted", status = HttpStatusCode.OK)
        }
        put{
            val new_branch = call.receive<Branch>()
            branchController.updateBranch(new_branch)
            call.respondText("Successfully updated",status=HttpStatusCode.OK)
        }
    }
}