package com.example.models

import kotlinx.serialization.Serializable

val customers_list = mutableListOf<Customer>()

@Serializable
data class Customer(
    val id:String,
    val name:String,
    val surname:String,
    val email:String,
    var password:String
)