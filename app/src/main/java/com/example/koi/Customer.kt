package com.example.koi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey val kode: Int,
    @ColumnInfo(name = "nama") val nama: String?,
    @ColumnInfo(name = "penjual") val penjual: String?
)