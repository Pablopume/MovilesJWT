package com.example.plantillaexamen.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.plantillaexamen.data.sources.service.ComidaRoomDao
import com.example.plantillaexamen.data.sources.service.CustomerRoomDao
import com.example.plantillaexamen.data.sources.service.IngredienteRoomDao
import com.example.plantillaexamen.domain.modelo.ComidaEntity
import com.example.plantillaexamen.domain.modelo.CustomerEntity
import com.example.plantillaexamen.domain.modelo.IngredienteEntity

@Database(entities = [CustomerEntity::class, ComidaEntity::class, IngredienteEntity::class] , version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerRoomDao
    abstract fun comidaDao(): ComidaRoomDao
    abstract fun ingredienteDao(): IngredienteRoomDao
}
