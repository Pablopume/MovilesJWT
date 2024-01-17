package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.ComidaRepository
import com.example.plantillaexamen.domain.modelo.Comida
import javax.inject.Inject

class AddComidaUseCase @Inject constructor(private val comidaRepository: ComidaRepository) {
    fun invoke(comida: Comida) = comidaRepository.insertComida(comida)
}