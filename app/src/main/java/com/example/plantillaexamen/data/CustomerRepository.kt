package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.sources.remote.RemoteDataSource
import com.example.plantillaexamen.data.sources.service.CustomerRoomDao
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.domain.modelo.CustomerEntity
import com.example.plantillaexamen.domain.modelo.toCustomerEntity
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


@ActivityRetainedScoped
class CustomerRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val customerRoomDao: CustomerRoomDao
) {

    suspend fun getCustomer(id: Int): NetworkResult<Customer> {
        return remoteDataSource.getCustomer(id)
    }


    fun insertCustomer(customers: List<Customer>) {
        val customerEntities = customers.map { customer ->
            CustomerEntity(
                id = customer.id,
                name = customer.name,
                lastName = customer.lastName,
                email = customer.email,
                phone = customer.phone,
                dob = customer.dob,
                isSelected = customer.isSelected
            )
        }
        customerRoomDao.insertCustomers(customerEntities)
    }


    fun getAllCustomers(): List<CustomerEntity> {
        return customerRoomDao.getAllCustomers()
    }

    fun insertOneCustomer(customer: Customer) {
        customerRoomDao.insertCustomer(customer.toCustomerEntity())
    }

    fun deleteCustomerRoom(id: Int) {
        customerRoomDao.deleteCustomer(id)
    }

    suspend fun getCustomers(): NetworkResult<List<Customer>> {
        return remoteDataSource.getCustomers()
    }

    suspend fun deleteCustomer(id: Int): NetworkResult<Unit> {
        return remoteDataSource.deleteCustomer(id)
    }
}