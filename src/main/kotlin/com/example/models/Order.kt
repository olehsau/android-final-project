package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val product_id:String,
    val quantity:Int
)