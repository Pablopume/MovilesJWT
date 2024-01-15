package com.example.plantillaexamen.framework.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantillaexamen.domain.usecases.AddOrderUseCase
import com.example.plantillaexamen.utils.NetworkResult
import com.example.restaurantapi.domain.modelo.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddViewModel @Inject constructor(private val addOrderUseCase: AddOrderUseCase) : ViewModel() {

    private val _state = MutableStateFlow(AddState(error = null))
    val state: StateFlow<AddState> get() = _state.asStateFlow()

    init {
        // No es necesario inicializar _state en el constructor ahora
    }

    suspend fun handleEvent(event: AddOrderEvent) {
        when (event) {
            is AddOrderEvent.AddOrder -> {
                addOrder(event.order)
            }

            is AddOrderEvent.ErrorVisto -> _state.value = _state.value.copy(error = null)
        }
    }

    private suspend fun addOrder(order: Order) {
        when (val result = addOrderUseCase(order)) {
            is NetworkResult.Success -> {
                _state.value = _state.value.copy(error = null)
            }

            is NetworkResult.Error -> {
                _state.value = _state.value.copy(error = result.message ?: "error")
            }
        }
    }
}
