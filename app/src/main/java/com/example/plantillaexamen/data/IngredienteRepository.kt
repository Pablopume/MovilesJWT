package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.sources.service.IngredienteRoomDao
import com.example.plantillaexamen.domain.modelo.Ingrediente
import com.example.plantillaexamen.domain.modelo.toIngredienteEntity
import javax.inject.Inject

class IngredienteRepository @Inject constructor(
    private val ingredienteRoomDao: IngredienteRoomDao,
    ) {
    fun getAllIngredientes(id: Int) = ingredienteRoomDao.getAllIngredientes(id)
    fun insertIngredientes(ingrediente: Ingrediente) = ingredienteRoomDao.insertIngredientes(ingrediente.toIngredienteEntity())
    fun deleteIngrediente(id: Int) = ingredienteRoomDao.deleteIngrediente(id)
}