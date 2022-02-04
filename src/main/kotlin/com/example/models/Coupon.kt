package com.example.models

import kotlinx.serialization.Serializable

val coupons_list = mutableListOf<Coupon>()

@Serializable
data class Coupon(
    val id:String,
    val discount:String,
    val min_order:Double
)