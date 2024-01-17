package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.ComidaRepository
import javax.inject.Inject

class DeleteComidaUseCase @Inject constructor(private val comidaRepository: ComidaRepository) {
    fun invoke(id: Int) = comidaRepository.deleteComida(id)
}