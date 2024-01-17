package com.example.plantillaexamen.domain.modelo

data class Ingrediente (
    val id: Int,
    val nombre: String,
    val comidaId: Int,
    var isSelected: Boolean = false
)
fun Ingrediente.toIngredienteEntity() : IngredienteEntity = IngredienteEntity(id, nombre, comidaId)