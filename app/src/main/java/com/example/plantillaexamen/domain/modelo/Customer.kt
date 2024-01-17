package com.example.plantillaexamen.domain.modelo

import java.time.LocalDate

data class Customer(
     val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val dob: LocalDate,

    var isSelected: Boolean = false
)
fun Customer.toCustomerEntity() : CustomerEntity = CustomerEntity(id, name, lastName, email, phone, dob)