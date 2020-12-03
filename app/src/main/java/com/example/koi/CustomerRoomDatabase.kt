package com.example.koi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Customer::class), version = 1)
abstract class CustomerRoomDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao

    abstract val applicationContext: Context
    val db = Room.databaseBuilder(
        applicationContext,
        CustomerRoomDatabase::class.java, "DBCS"
    ).build()

}