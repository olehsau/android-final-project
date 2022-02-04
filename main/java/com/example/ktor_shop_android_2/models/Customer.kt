package com.example.ktor_shop_android_2.models

data class Customer(
    var id:String, var name:String, var surname:String, var email:String, val password:String
)