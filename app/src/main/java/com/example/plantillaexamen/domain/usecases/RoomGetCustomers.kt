package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.CustomerRepository
import javax.inject.Inject

class RoomGetCustomers @Inject constructor(private val customerRepository: CustomerRepository) {
     operator fun invoke() = customerRepository.getAllCustomers()
}