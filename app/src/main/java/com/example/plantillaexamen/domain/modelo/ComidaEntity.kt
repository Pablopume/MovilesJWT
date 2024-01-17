package com.example.plantillaexamen.domain.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantillaexamen.domain.ConstantesDomain

@Entity(tableName = ConstantesDomain.COMIDA_TABLE)
data class ComidaEntity (
   @PrimaryKey val id: Int,
    val nombre: String,
)
fun ComidaEntity.toComida() : Comida = Comida(id, nombre, false)

