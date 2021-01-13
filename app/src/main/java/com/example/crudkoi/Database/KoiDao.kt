package com.example.crudkoi.Database

import androidx.room.*

@Dao
interface KoiDao {
    @Insert
    suspend fun addKoi(koi: Koi)

    @Update
    suspend fun updateKoi(koi: Koi)

    @Delete
    suspend fun deleteKoi(koi: Koi)

    @Query("SELECT * FROM koi")
    suspend fun getAllKoi(): List<Koi>

    @Query("SELECT * FROM koi WHERE id=:koi_id")
    suspend fun getKoi(koi_id: Int) : List<Koi>

}