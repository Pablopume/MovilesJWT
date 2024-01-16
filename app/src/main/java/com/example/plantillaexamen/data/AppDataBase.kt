package com.example.plantillaexamen.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.plantillaexamen.data.sources.service.CustomerRoomDao
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.domain.modelo.CustomerEntity

@Database(entities = [CustomerEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerRoomDao
}
