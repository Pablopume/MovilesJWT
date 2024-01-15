package com.example.plantillaexamen.domain.usecases


import com.example.plantillaexamen.data.CustomerRepository
import com.example.plantillaexamen.utils.NetworkResult
import com.example.plantillaexamen.domain.modelo.Customer
import javax.inject.Inject

class DeleteCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
)   {
    suspend operator fun invoke(customer: Customer): NetworkResult<Unit> {
        return customerRepository.deleteCustomer(customer.id)
    }
}