package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.ComidaRepository
import javax.inject.Inject

class GetAllComidasUseCase @Inject constructor(private val comidaRepository: ComidaRepository){
    fun invoke() = comidaRepository.getAllComidas()
}