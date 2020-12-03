package com.example.koi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AdminDao {
    @Query("SELECT * FROM admin")
    fun getAll(): List<Admin>

    @Query("SELECT * FROM admin WHERE kode IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Admin>

    @Query("SELECT * FROM admin WHERE nama LIKE :nama AND " +
            "username LIKE :username LIMIT 1")
    fun findByName(nama: String, username: String): Admin

    @Insert
    fun insertAll(vararg admin: Admin)

    @Delete
    fun delete(admin: Admin)
}