package com.example.koi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KoiDao {
    @Query("SELECT * FROM koi")
    fun getAll(): List<Koi>

    @Query("SELECT * FROM admin WHERE kode IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Koi>

    @Query("SELECT * FROM admin WHERE nama LIKE :nama AND " +
            "username LIKE :username LIMIT 1")
    fun findByName(nama: String, username: String): Koi

    @Insert
    fun insertAll(vararg koi: Koi)

    @Delete
    fun delete(koi: Koi)
}