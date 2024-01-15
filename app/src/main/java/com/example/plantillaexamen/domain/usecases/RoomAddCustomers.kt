package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.CustomerRepository
import com.example.plantillaexamen.domain.modelo.Customer
import javax.inject.Inject

class RoomAddCustomers @Inject constructor(private val customerRepository: CustomerRepository) {
     operator fun invoke(customers: List<Customer>) = customerRepository.insertCustomer(customers)
}