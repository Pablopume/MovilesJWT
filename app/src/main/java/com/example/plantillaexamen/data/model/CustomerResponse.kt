package com.example.plantillaexamen.data.model

import com.example.plantillaexamen.data.sources.Constantes
import com.example.plantillaexamen.domain.modelo.Customer
import com.google.gson.annotations.SerializedName
import java.time.LocalDate


data class CustomerResponse(
@SerializedName(Constantes.id)
val id: Int,
@SerializedName(Constantes.name)
val name: String,
@SerializedName(Constantes.lastName)
val lastName: String,
@SerializedName(Constantes.email)
val email: String,
@SerializedName(Constantes.phone)
val phone: String,
@SerializedName(Constantes.dob)
val dob: String,
)

fun CustomerResponse.toCustomer() : Customer = Customer(id, name, lastName, email, phone,LocalDate.parse(dob),false )
