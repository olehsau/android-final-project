package com.example.controllers

import com.example.models.Branch
import com.example.modelsdb.BranchDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class BranchController {
    fun addBranch(branch:Branch){
        transaction {
            BranchDB.insert {
                it[BranchId] = branch.id
                it[Address] = branch.address
                it[Area] = branch.area
                it[Phone] = branch.phone
                it[ManagerId] = branch.manager_id
            }
        }
    }

    fun getBranchById(id:String):ArrayList<Branch>{
        val branch:ArrayList<Branch> = ArrayList()
        transaction {
            BranchDB.select(BranchDB.BranchId eq id).map{
                branch.add(Branch(it[BranchDB.BranchId],it[BranchDB.Address],it[BranchDB.Area],it[BranchDB.Phone],it[BranchDB.ManagerId]))
            }
        }
        return branch
    }

    fun getAllBranches():ArrayList<Branch>{
        val branches:ArrayList<Branch> = ArrayList()
        transaction {
            BranchDB.selectAll().map{
                branches.add(Branch(it[BranchDB.BranchId],it[BranchDB.Address],it[BranchDB.Area],it[BranchDB.Phone],it[BranchDB.ManagerId]))
            }
        }
        return branches
    }

    fun deleteBranchById(id:String){
        transaction {
            BranchDB.deleteWhere{BranchDB.BranchId eq id}
        }
    }

    fun updateBranch(newBranch:Branch){
        transaction {
            BranchDB.update({BranchDB.BranchId eq newBranch.id}){
                it[BranchId] = newBranch.id
                it[Address] = newBranch.address
                it[Area] = newBranch.area
                it[Phone] = newBranch.phone
                it[ManagerId] = newBranch.manager_id
            }
        }
    }
}