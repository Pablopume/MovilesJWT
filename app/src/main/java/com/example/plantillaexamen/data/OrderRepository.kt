package com.example.plantillaexamen.data


import com.example.plantillaexamen.data.sources.remote.RemoteDataSource
import com.example.plantillaexamen.utils.NetworkResult
import com.example.restaurantapi.domain.modelo.Order

import javax.inject.Inject


class OrderRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    private suspend fun getOrders(): NetworkResult<List<Order>> {
        return remoteDataSource.getOrders()
    }

    suspend fun createOrder(order: Order): NetworkResult<Order> {
        return remoteDataSource.createOrder(order)
    }

    suspend fun deleteOrder(id: Int): NetworkResult<Unit> {
        return remoteDataSource.deleteOrder(id)
    }

    suspend fun getOrdersPorId(id: Int): List<Order> {
        var list: List<Order> = getOrders().data ?: emptyList()
        list = list.filter { it.customerId == id }
        return list
    }

}