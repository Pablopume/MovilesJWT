package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.CustomerRepository
import com.example.plantillaexamen.domain.modelo.Customer
import javax.inject.Inject

class RoomAddCustomer @Inject constructor(private val customerRepository: CustomerRepository) {
     operator fun invoke(customer: Customer) = customerRepository.insertOneCustomer(customer)
}