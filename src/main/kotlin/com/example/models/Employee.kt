package com.example.models

import kotlinx.serialization.Serializable

val employees_list = mutableListOf<Employee>()

@Serializable
data class Employee(
    var id:String,
    var post:String,
    var name:String,
    var surname:String,
    var email:String
)