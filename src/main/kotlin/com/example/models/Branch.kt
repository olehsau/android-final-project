package com.example.models

import kotlinx.serialization.Serializable

val branches_list = mutableListOf<Branch>()

@Serializable
data class Branch(
    val id:String,
    val address:String,
    val area:String,
    var phone:String,
    var manager_id:String
)