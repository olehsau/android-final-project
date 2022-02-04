package com.example.controllers

import com.example.models.Customer
import com.example.models.Employee
import com.example.modelsdb.CustomerDB
import com.example.modelsdb.EmployeeDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EmployeeController {
    fun addEmployee(employee:Employee){
        transaction {
            EmployeeDB.insert{
                it[EmployeeId] = employee.id
                it[Post] = employee.post
                it[Name] = employee.name
                it[Surname] = employee.surname
                it[Email] = employee.email
            }
        }
    }

    fun getEmployeeById(id:String):ArrayList<Employee>{
        val employee:ArrayList<Employee> = ArrayList()
        transaction {
            EmployeeDB.select(EmployeeDB.EmployeeId eq id).map{
                employee.add(Employee(it[EmployeeDB.EmployeeId],it[EmployeeDB.Post],it[EmployeeDB.Name],it[EmployeeDB.Surname],it[EmployeeDB.Email]))
            }
        }
        return employee
    }

    fun getAllEmployees():ArrayList<Employee>{
        val employees:ArrayList<Employee> = ArrayList()
        transaction {
            EmployeeDB.selectAll().map{
                employees.add(Employee(it[EmployeeDB.EmployeeId],it[EmployeeDB.Post],it[EmployeeDB.Name],it[EmployeeDB.Surname],it[EmployeeDB.Email]))
            }
        }
        return employees
    }

    fun deleteEmployeeById(id:String){
        transaction {
            EmployeeDB.deleteWhere { EmployeeDB.EmployeeId eq id }
        }
    }

    fun updateEmployee(newEmployee:Employee){
        transaction {
            EmployeeDB.update({EmployeeDB.EmployeeId eq newEmployee.id}){
                it[EmployeeId] = newEmployee.id
                it[Post] = newEmployee.post
                it[Name] = newEmployee.name
                it[Surname] = newEmployee.surname
                it[Email] = newEmployee.email
            }
        }
    }
}