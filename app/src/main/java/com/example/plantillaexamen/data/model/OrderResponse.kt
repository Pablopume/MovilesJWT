package com.example.plantillaexamen.data.model


import com.example.plantillaexamen.data.sources.Constantes
import com.example.restaurantapi.domain.modelo.Order
import com.google.gson.annotations.SerializedName
import java.time.LocalDate


data class OrderResponse (
    @SerializedName(Constantes.id)
    val id: Int,
    @SerializedName(Constantes.customerId)
    val customerId: Int,
    @SerializedName(Constantes.orderDate)
    val orderDate: String,
    @SerializedName(Constantes.tableId)
    val tableId: Int,
)
fun OrderResponse.toOrder() : Order = Order(id, customerId, LocalDate.parse(orderDate), tableId )