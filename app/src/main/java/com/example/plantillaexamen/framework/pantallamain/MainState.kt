package com.example.plantillaexamen.framework.pantallamain

import com.example.plantillaexamen.domain.modelo.Customer

data class MainState (val personas: List<Customer> = emptyList(),
                      val personasSeleccionadas: List<Customer> = emptyList(),
                      val selectMode: Boolean = false,
                      val error: String? = null,)