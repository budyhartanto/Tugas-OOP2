package com.example.koi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Admin::class), version = 1)
abstract class AdminRoomDatabase : RoomDatabase() {
    abstract fun AdminDao(): AdminDao

    abstract val applicationContext: Context
    val db = Room.databaseBuilder(
        applicationContext,
        AdminRoomDatabase::class.java, "DBCS"
    ).build()

}