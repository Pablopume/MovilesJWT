package com.example.plantillaexamen.domain.modelo

data class Comida (
    val id: Int,
    val nombre: String,
    var isSelected: Boolean = false
)
fun Comida.toComidaEntity() : ComidaEntity = ComidaEntity(id, nombre)