package com.example.models

import kotlinx.serialization.Serializable


val products:MutableList<Product> = mutableListOf<Product>()

@Serializable
data class Product(
    val id: String,
    val name: String,
    val price: Double
)