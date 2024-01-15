package com.example.plantillaexamen.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantillaexamen.data.sources.service.CustomerRoomService
import com.example.plantillaexamen.domain.modelo.Customer

@Database(entities = [Customer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerRoomService
}
