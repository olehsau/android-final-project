package com.example.routes

import com.example.controllers.OrderController
import com.example.models.Order
import com.example.models.products
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.cartRouting(){
    val orderController = OrderController()
    route("/cart"){
        get{
            call.respond(orderController.getAllOrders())
        }
        get("/{id}"){
            val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            call.respond(orderController.getOrderByProductId(id))
        }
        post{
            val new_item: Order = call.receive<Order>()
            orderController.addOrder(new_item)
            call.respondText("Added", status = HttpStatusCode.Created)
        }
        post("?id={id}&quantity={quantity}"){
            val id = call.parameters["id"] ?: return@post call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            val quantityStr = call.parameters["quantity"] ?: return@post call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            val quantity = quantityStr.toInt() ?: return@post call.respondText("bad quantity", status = HttpStatusCode.BadRequest)
            orderController.addOrder(Order(id,quantity))
            call.respondText("Added", status=HttpStatusCode.Created)
        }
        delete("/{id}"){
            val id = call.parameters["id"] ?: return@delete call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            orderController.deleteOrderByProductId(id)
            call.respondText("deleted", status = HttpStatusCode.OK)
        }
    }
}