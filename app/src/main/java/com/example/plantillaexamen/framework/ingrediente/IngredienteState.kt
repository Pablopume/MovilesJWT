package com.example.plantillaexamen.framework.ingrediente


import com.example.plantillaexamen.domain.modelo.Ingrediente

data class IngredienteState (
    val personas: List<Ingrediente> = emptyList(),
    val personasSeleccionadas: List<Ingrediente> = emptyList(),
    val selectMode: Boolean = false,
    val error: String? = null,
)