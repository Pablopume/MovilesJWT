package com.example.plantillaexamen.data.sources.service

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantillaexamen.domain.modelo.Customer

interface CustomerRoomService {
    @Query("SELECT * FROM customer_table")
    fun getAllCustomers(): List<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomers(customers: List<Customer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Query("DELETE FROM customer_table WHERE id = :id")
    fun deleteCustomer(id: Int)
}