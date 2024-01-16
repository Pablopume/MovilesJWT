package com.example.plantillaexamen.domain.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
@Entity(tableName = "customer_table")
data class CustomerEntity

(
        @PrimaryKey val id: Int,
        val name: String,
        val lastName: String,
        val email: String,
        val phone: String,
        val dob: LocalDate,
        var isSelected: Boolean = false
    )
fun CustomerEntity.toCustomer() : Customer = Customer(id, name, lastName, email, phone, dob)