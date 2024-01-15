package com.example.plantillaexamen.domain.usecases


import com.example.plantillaexamen.data.OrderRepository
import com.example.plantillaexamen.utils.NetworkResult

import com.example.restaurantapi.domain.modelo.Order

import javax.inject.Inject

class AddOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(order: Order): NetworkResult<Order> {
        return repository.createOrder(order)
    }
}