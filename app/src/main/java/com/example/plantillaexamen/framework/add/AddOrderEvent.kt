package com.example.plantillaexamen.framework.add


import com.example.restaurantapi.domain.modelo.Order

sealed class AddOrderEvent {



    class AddOrder(val order: Order) : AddOrderEvent()

    data object ErrorVisto : AddOrderEvent()



}
