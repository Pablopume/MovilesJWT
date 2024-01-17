package com.example.plantillaexamen.framework.comida

import com.example.plantillaexamen.domain.modelo.Comida

data class ComidaState (
    val personas: List<Comida> = emptyList(),
    val personasSeleccionadas: List<Comida> = emptyList(),
    val selectMode: Boolean = false,
    val error: String? = null,
)