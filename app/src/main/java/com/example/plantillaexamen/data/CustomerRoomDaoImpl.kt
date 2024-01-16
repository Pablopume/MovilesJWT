package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.sources.service.CustomerRoomDao
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.domain.modelo.CustomerEntity
import javax.inject.Inject

class CustomerRoomDaoImpl @Inject constructor(private val customerDao: CustomerRoomDao) : CustomerRoomDao {
    override fun getAllCustomers(): List<CustomerEntity> {
        return customerDao.getAllCustomers()
    }

    override fun insertCustomers(customers: List<CustomerEntity>) {
        return customerDao.insertCustomers(customers)
    }

    override fun insertCustomer(customer: CustomerEntity) {
return  customerDao.insertCustomer(customer)  }

    override fun deleteCustomer(id: Int) {
        return customerDao.deleteCustomer(id)
    }
}