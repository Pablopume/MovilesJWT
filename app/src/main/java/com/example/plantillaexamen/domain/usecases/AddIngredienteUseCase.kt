package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.IngredienteRepository
import com.example.plantillaexamen.domain.modelo.Ingrediente
import javax.inject.Inject

class AddIngredienteUseCase @Inject constructor(private val ingredienteRepository: IngredienteRepository) {
    fun invoke(ingrediente: Ingrediente) = ingredienteRepository.insertIngredientes(ingrediente)
}