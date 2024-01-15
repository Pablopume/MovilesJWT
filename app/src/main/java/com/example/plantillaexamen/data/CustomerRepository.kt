package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.model.toCustomer
import com.example.plantillaexamen.data.sources.remote.RemoteDataSource
import com.example.plantillaexamen.data.sources.service.CustomerRoomService
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.data.sources.service.CustomerService
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.time.LocalDate
import javax.inject.Inject


@ActivityRetainedScoped
class CustomerRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val customerService: CustomerService,
    private val customerRoomService: CustomerRoomService
) {

    suspend fun getCustomer(id: Int): NetworkResult<Customer> {
        return remoteDataSource.getCustomer(id)
    }


    fun insertCustomer(customers: List<Customer>) {
        customerRoomService.insertCustomers(customers)
    }

    fun getAllCustomers(): List<Customer> {
        return customerRoomService.getAllCustomers()
    }

    fun insertOneCustomer(customer: Customer) {
        customerRoomService.insertCustomer(customer)
    }

    fun deleteCustomerRoom(id: Int) {
        customerRoomService.deleteCustomer(id)
    }

    suspend fun getCustomers(): NetworkResult<List<Customer>> {
        return remoteDataSource.getCustomers()
    }

    suspend fun deleteCustomer(id: Int): NetworkResult<Unit> {
        return remoteDataSource.deleteCustomer(id)
    }
}