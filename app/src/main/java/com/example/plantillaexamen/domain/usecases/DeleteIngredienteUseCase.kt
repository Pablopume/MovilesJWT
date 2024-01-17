package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.IngredienteRepository
import javax.inject.Inject

class DeleteIngredienteUseCase @Inject constructor(private val ingredienteRepository: IngredienteRepository) {
    fun invoke(id:Int ) = ingredienteRepository.deleteIngrediente(id)
}