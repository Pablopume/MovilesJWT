package com.example.plantillaexamen.domain.usecases


import com.example.plantillaexamen.data.CustomerRepository
import com.example.plantillaexamen.utils.NetworkResult
import com.example.plantillaexamen.domain.modelo.Customer
import javax.inject.Inject

class GetAllCustomersUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Customer>> {
        return customerRepository.getCustomers()
    }
}