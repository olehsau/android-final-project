package com.example.modelsdb

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object OrderDB : Table(){
    var ProductId: Column<String> = varchar("product_id",50).references(ProductDB.ProductId).uniqueIndex()
    var Quantity: Column<Int> = integer("quantity")
}