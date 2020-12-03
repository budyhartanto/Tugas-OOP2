package com.example.koi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
data class Admin(
    @PrimaryKey val kode: Int,
    @ColumnInfo(name = "nama") val nama: String?,
    @ColumnInfo(name = "username") val username: String?
)