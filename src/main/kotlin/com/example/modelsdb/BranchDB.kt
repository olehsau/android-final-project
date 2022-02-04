package com.example.modelsdb

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column

object BranchDB : Table() {
    var BranchId : Column<String> = varchar("id",50).uniqueIndex()
    var Address : Column<String> = varchar("address",50)
    var Area : Column<String> = varchar("area",50)
    var Phone : Column<String> = varchar("phone",50)
    var ManagerId : Column<String> = varchar("manager_id",50).references(EmployeeDB.EmployeeId)
}