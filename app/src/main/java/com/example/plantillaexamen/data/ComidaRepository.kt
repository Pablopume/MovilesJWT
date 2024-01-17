package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.sources.service.ComidaRoomDao
import com.example.plantillaexamen.domain.modelo.Comida
import com.example.plantillaexamen.domain.modelo.toComidaEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ComidaRepository @Inject constructor(private val comidaRoomDao: ComidaRoomDao) {
    fun getAllComidas() = comidaRoomDao.getAllComidas()
    fun insertComida(comida: Comida) = comidaRoomDao.insertComidas(comida.toComidaEntity())
    fun deleteComida(id: Int) = comidaRoomDao.deleteComida(id)


}