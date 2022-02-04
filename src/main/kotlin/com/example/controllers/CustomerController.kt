package com.example.controllers

import com.example.models.Coupon
import com.example.models.Customer
import com.example.modelsdb.CouponDB
import com.example.modelsdb.CustomerDB
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class CustomerController {
    fun addCustomer(customer: Customer){
        transaction {
            CustomerDB.insert{
                it[CustomerId] = customer.id.toString()
                it[Name] = customer.name
                it[Surname] = customer.surname
                it[Email] = customer.email
                it[Password] = customer.password.toString()
            }
        }
    }

    fun getCustomerById(id:String):ArrayList<Customer>{
        val customer:ArrayList<Customer> = ArrayList()
        transaction {
            CustomerDB.select(CustomerDB.CustomerId eq id).map{
                customer.add(Customer(it[CustomerDB.CustomerId].toString(),it[CustomerDB.Name],it[CustomerDB.Surname],it[CustomerDB.Email],it[CustomerDB.Password].toString()))
            }
        }
        return customer
    }

    fun getAllCustomers():ArrayList<Customer>{
        val customers:ArrayList<Customer> = ArrayList()
        transaction {
            CustomerDB.selectAll().map{
                customers.add(Customer(it[CustomerDB.CustomerId].toString(),it[CustomerDB.Name],it[CustomerDB.Surname],it[CustomerDB.Email],it[CustomerDB.Password].toString()))
            }
        }
        return customers
    }

    fun deleteCustomerById(id:String){
        transaction {
            CustomerDB.deleteWhere { CustomerDB.CustomerId eq id }
        }
    }

    fun changePasswordById(id:String, newPassword:String){
        val customer:Customer = getCustomerById(id)[0]
        customer.password = newPassword
        deleteCustomerById(id)
        addCustomer(customer)
    }
}