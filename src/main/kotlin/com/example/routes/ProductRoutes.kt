package com.example.routes

import com.example.controllers.ProductController
import com.example.models.Product
import com.example.models.products
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.productRouting(){
    val productController = ProductController()
    route("/product"){
        get{
            call.respond(productController.getAllProducts())
        }
        get("/{id}"){
            val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            call.respond(productController.getProductById(id))
        }
        post{
            val new_product = call.receive<Product>()
            productController.addProduct(new_product)
            call.respondText("added", status=HttpStatusCode.OK)
        }
        delete("/{id}"){
            val id = call.parameters["id"] ?: return@delete call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            productController.deleteProductById(id)
            call.respondText("Product deleted", status = HttpStatusCode.OK)
        }
    }
}