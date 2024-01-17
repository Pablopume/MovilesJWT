package com.example.plantillaexamen.data.sources.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantillaexamen.data.sources.Constantes
import com.example.plantillaexamen.domain.modelo.ComidaEntity

@Dao
interface ComidaRoomDao {

    @Query(Constantes.QUERY)
    fun getAllComidas(): List<ComidaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComidas(comida: ComidaEntity)

    @Query(Constantes.Query2)
    fun deleteComida(id: Int)
}