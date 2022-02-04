package com.example.controllers

import com.example.models.Order
import com.example.modelsdb.OrderDB
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderController {
    fun addOrder(item: Order){
        transaction {
            OrderDB.insert {
                it[ProductId] = item.product_id
                it[Quantity] = item.quantity
            }
        }
    }

    fun getAllOrders():ArrayList<Order>{
        val orders:ArrayList<Order> = ArrayList()
        transaction {
            OrderDB.selectAll().map{
                orders.add(Order(it[OrderDB.ProductId],it[OrderDB.Quantity]))
            }
        }
        return orders
    }

    fun getOrderByProductId(id:String):ArrayList<Order>{
        val order:ArrayList<Order> = ArrayList()
        transaction {
            OrderDB.select(OrderDB.ProductId eq id).map{
                order.add(Order(it[OrderDB.ProductId],it[OrderDB.Quantity]))
            }
        }
        return order
    }

    fun deleteOrderByProductId(id:String) {
        transaction {
            OrderDB.deleteWhere { OrderDB.ProductId eq id }
        }
    }
}