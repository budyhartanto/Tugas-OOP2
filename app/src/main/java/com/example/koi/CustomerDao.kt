package com.example.koi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer")
    fun getAll(): List<Customer>

    @Query("SELECT * FROM customer WHERE kode IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Customer>

    @Query("SELECT * FROM customer WHERE nama LIKE :nama AND " +
            "pelanggan LIKE :pelanggan LIMIT 1")
    fun findByName(nama: String, penjual: String): Customer

    @Insert
    fun insertAll(vararg customer: Customer)

    @Delete
    fun delete(customer: Customer)
}