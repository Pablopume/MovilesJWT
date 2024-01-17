package com.example.plantillaexamen.data.sources.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantillaexamen.data.sources.Constantes
import com.example.plantillaexamen.domain.modelo.IngredienteEntity

@Dao
interface IngredienteRoomDao {

    @Query(Constantes.QUERY5)
    fun getAllIngredientes(id: Int): List<IngredienteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredientes(ingrediente: IngredienteEntity)

    @Query(Constantes.QUERY6)
    fun deleteIngrediente(id: Int)
}