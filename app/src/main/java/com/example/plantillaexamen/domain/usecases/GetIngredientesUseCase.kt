package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.IngredienteRepository
import javax.inject.Inject

class GetIngredientesUseCase @Inject constructor(private val ingredienteRepository: IngredienteRepository) {
fun invoke(id: Int) = ingredienteRepository.getAllIngredientes(id)
}