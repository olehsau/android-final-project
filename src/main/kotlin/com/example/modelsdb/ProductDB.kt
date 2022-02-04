package com.example.modelsdb

import com.example.modelsdb.CustomerDB.uniqueIndex
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProductDB : Table(){
    var ProductId: Column<String> = varchar("id",50).uniqueIndex()
    var Name: Column<String> = varchar("name",50)
    var Price : Column <Double> = double("price")
}