package com.example.modelsdb

import com.example.modelsdb.CouponDB.uniqueIndex
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CustomerDB : Table(){
    var CustomerId: Column<String> = varchar("id",50).uniqueIndex()
    var Name: Column<String> = varchar("name",50)
    var Surname: Column<String> = varchar("surname",50)
    var Email: Column<String> = varchar("email",50)
    var Password: Column<String> = varchar("password",50)
}