package com.example.plantillaexamen.data.sources.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantillaexamen.data.sources.Constantes
import com.example.plantillaexamen.domain.modelo.CustomerEntity

@Dao
interface CustomerRoomDao {
    @Query(Constantes.QUERY3)
    fun getAllCustomers(): List<CustomerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomers(customers: List<CustomerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: CustomerEntity)

    @Query(Constantes.QUERY4)
    fun deleteCustomer(id: Int)
}