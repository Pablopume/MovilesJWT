package com.example.plantillaexamen.data.sources.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.domain.modelo.CustomerEntity

@Dao
interface CustomerRoomDao {
    @Query("SELECT * FROM customer_table")
    fun getAllCustomers(): List<CustomerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomers(customers: List<CustomerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: CustomerEntity)

    @Query("DELETE FROM customer_table WHERE id = :id")
    fun deleteCustomer(id: Int)
}