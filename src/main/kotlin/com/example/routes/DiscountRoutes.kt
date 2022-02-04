package com.example.routes

import com.example.controllers.CouponController
import com.example.models.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.selects.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.couponRouting(){
    val couponController = CouponController()
    route("/coupon"){
        get{
            call.respond(couponController.getAllCoupons())
        }
        get("/{id}"){
            val id:String = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            call.respond(couponController.getCouponById(id))
        }
        post{
            val coupon: Coupon = call.receive()
            couponController.addCoupon(coupon)
            call.respondText("Coupon added", status=HttpStatusCode.Created)
        }
        delete("/{id}"){
            val id = call.parameters["id"] ?: return@delete call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            couponController.deleteCouponById(id)
            call.respondText("Coupon deleted", status=HttpStatusCode.OK)
        }
        put{
            val newCoupon = call.receive<Coupon>()
            couponController.updateCoupon(newCoupon)
            call.respondText("Updated successfully.", status=HttpStatusCode.OK)
        }
    }
}