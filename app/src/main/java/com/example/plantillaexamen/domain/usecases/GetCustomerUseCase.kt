package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.CustomerRepository
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(id: Int) = customerRepository.getCustomer(id)

}