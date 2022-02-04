package com.example.controllers

import com.example.models.Coupon
import com.example.models.Customer
import com.example.models.coupons_list
import com.example.modelsdb.CouponDB
import com.example.modelsdb.CustomerDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.sqlite.core.DB

class CouponController {
    fun addCoupon(coupon: Coupon){
        transaction{
            CouponDB.insert{
                it[CouponId] = coupon.id
                it[Discount] = coupon.discount
                it[MinOrder] = coupon.min_order
            }
        }
    }

    fun getCouponById(id:String):ArrayList<Coupon> {
        val coupon: ArrayList<Coupon> = ArrayList()
        transaction {
            CouponDB.select(CouponDB.CouponId eq id).map {
                coupon.add(
                    Coupon(
                        it[CouponDB.CouponId],
                        it[CouponDB.Discount],
                        it[CouponDB.MinOrder]
                    )
                )
            }
        }
        return coupon
    }

    fun getAllCoupons():ArrayList<Coupon>{
        val coupons:ArrayList<Coupon> = ArrayList()
        transaction {
            CouponDB.selectAll().map{
                coupons.add(Coupon(it[CouponDB.CouponId],it[CouponDB.Discount],it[CouponDB.MinOrder]))
            }
        }
        return coupons
    }

    fun deleteCouponById(id:String){
        transaction {
            CouponDB.deleteWhere { CouponDB.CouponId eq id }
        }
    }

    fun updateCoupon(newCoupon:Coupon){
        transaction {
            CouponDB.update({CouponDB.CouponId eq newCoupon.id}){
                it[CouponId] = newCoupon.id
                it[Discount] = newCoupon.discount
                it[MinOrder] = newCoupon.min_order
            }
        }
    }
}