package com.example.plantillaexamen.domain.usecases
import com.example.plantillaexamen.data.OrderRepository
import com.example.restaurantapi.domain.modelo.Order
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(id: Int) :List<Order>{
        return orderRepository.getOrdersPorId(id)
    }
}