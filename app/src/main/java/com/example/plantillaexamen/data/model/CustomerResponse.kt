package com.example.plantillaexamen.data.model

import com.example.plantillaexamen.domain.modelo.Customer
import com.google.gson.annotations.SerializedName
import java.time.LocalDate


data class CustomerResponse(
@SerializedName("id")
val id: Int,
@SerializedName("firstName")
val name: String,
@SerializedName("lastName")
val lastName: String,
@SerializedName("email")
val email: String,
@SerializedName("phone")
val phone: String,
@SerializedName("dob")
val dob: String,
)

fun CustomerResponse.toCustomer() : Customer = Customer(id, name, lastName, email, phone,LocalDate.parse(dob) )
