package com.example.plantillaexamen.domain.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantillaexamen.domain.ConstantesDomain

@Entity(tableName = ConstantesDomain.INGREDIENTE_TABLE)
data class IngredienteEntity (
  @PrimaryKey  val id: Int,
    val nombre: String,
  val comidaId: Int
)
fun IngredienteEntity.toIngrediente() : Ingrediente = Ingrediente(id, nombre, comidaId,false)