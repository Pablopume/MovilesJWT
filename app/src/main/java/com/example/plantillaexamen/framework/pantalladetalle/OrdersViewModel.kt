package com.example.plantillaexamen.framework.pantalladetalle

import androidx.lifecycle.ViewModel
import com.example.plantillaexamen.domain.usecases.AddOrderUseCase
import com.example.plantillaexamen.domain.usecases.DeleteOrderUseCase
import com.example.plantillaexamen.domain.usecases.GetAllOrdersUseCase
import com.example.plantillaexamen.domain.usecases.GetCustomerUseCase
import com.example.plantillaexamen.utils.NetworkResult
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.framework.ConstantesFramework
import com.example.restaurantapi.domain.modelo.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getCustomerUseCase: GetCustomerUseCase,
    private val addOrderUseCase: AddOrderUseCase,
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase
) : ViewModel() {

    private val listOrders = mutableListOf<Order>()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    private val _uiState = MutableStateFlow(OrdersState())
    val uiState: StateFlow<OrdersState> get() = _uiState.asStateFlow()

    init {
        _uiState.value = OrdersState(
            personas = emptyList(),
            customerActual = null
        )
    }

     suspend fun handleEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.GetOrders -> {
                getOrders(event.id)
            }

            is OrderEvent.GetCustomer -> {
                getCustomer(event.id)
            }

            is OrderEvent.AddOrder -> {
                addOrder(event.order)
            }

            OrderEvent.ErrorVisto -> _uiState.value = _uiState.value.copy(error = null)

            is OrderEvent.DeletePersona -> {
                deleteOrder(event.order)
            }
        }
    }

    private suspend fun getCustomer(id: Int) {
        when (val result = getCustomerUseCase.invoke(id)) {
            is NetworkResult.Error<*> -> _error.value = result.message ?: ConstantesFramework.ERROR
            is NetworkResult.Success<*> -> {
                _uiState.value = _uiState.value.copy(customerActual = result.data as Customer)
            }
        }
    }

    private suspend fun addOrder(order: Order) {
        when (val result = addOrderUseCase.invoke(order)) {
            is NetworkResult.Error<*> -> _error.value = result.message ?: ConstantesFramework.ERROR
            is NetworkResult.Success<*> -> {
                if (result.data is Order) {
                    getOrders(order.customerId)
                }
            }
        }
    }

    private suspend fun getOrders(id: Int) {
        val result = getAllOrdersUseCase.invoke(id)
        listOrders.clear()
        listOrders.addAll(result)
        _uiState.value = _uiState.value.copy(personas = listOrders)
    }

    private suspend fun deleteOrder(orders: List<Order>) {
        val copiaPersonas = orders.toList()
        val personasParaEliminar = mutableListOf<Order>()
        var isSuccessful = true

        for (persona in copiaPersonas) {
            val result = deleteOrderUseCase.invoke(persona)

            if (result is NetworkResult.Error<*>) {
                _error.value = ConstantesFramework.ERROR
                isSuccessful = false
            } else {
                personasParaEliminar.add(persona)
            }
        }

        if (isSuccessful) {
            listOrders.removeAll(personasParaEliminar)
            _uiState.value = _uiState.value.copy(personas = listOrders)
        }
    }

    private suspend fun deleteOrder(persona: Order) {
        deleteOrder(listOf(persona))
    }
}
