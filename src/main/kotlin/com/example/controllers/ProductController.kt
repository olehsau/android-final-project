package com.example.controllers

import com.example.models.Product
import com.example.modelsdb.ProductDB
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ProductController {
    fun addProduct(newProduct:Product){
        transaction {
            ProductDB.insert{
                it[ProductId]=newProduct.id
                it[Price]=newProduct.price
                it[Name]=newProduct.name
            }
        }
    }

    fun getProductById(id:String):ArrayList<Product>{
        val products:ArrayList<Product> = ArrayList()
        transaction {
            ProductDB.select { ProductDB.ProductId eq id }.map{
                products.add(Product(it[ProductDB.ProductId],it[ProductDB.Name],it[ProductDB.Price]))
            }
        }
        return products
    }

    fun getAllProducts():ArrayList<Product>{
        val products:ArrayList<Product> = ArrayList()
        transaction {
            ProductDB.selectAll().map{
                products.add(Product(it[ProductDB.ProductId],it[ProductDB.Name],it[ProductDB.Price]))
            }
        }
        return products
    }

    fun deleteProductById(id:String) {
        transaction {
            ProductDB.deleteWhere { ProductDB.ProductId eq id }
        }
    }
}