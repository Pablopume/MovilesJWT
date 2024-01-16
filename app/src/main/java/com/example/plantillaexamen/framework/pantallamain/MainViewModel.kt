package com.example.plantillaexamen.framework.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.domain.modelo.toCustomer
import com.example.plantillaexamen.domain.usecases.DeleteCustomerUseCase
import com.example.plantillaexamen.domain.usecases.GetAllCustomersUseCase
import com.example.plantillaexamen.domain.usecases.RoomAddCustomer
import com.example.plantillaexamen.domain.usecases.RoomAddCustomers
import com.example.plantillaexamen.domain.usecases.RoomDeleteCustomer
import com.example.plantillaexamen.domain.usecases.RoomGetCustomers
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
    private val getAllCustomersUseCase: GetAllCustomersUseCase,
    private val roomGetCustomers: RoomGetCustomers,
    private val roomAddCustomer: RoomAddCustomers,
    private val roomDeleteCustomer: RoomDeleteCustomer
) : ViewModel() {

    private val listaPersonas = mutableListOf<Customer>()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    private var selectedPersonas = mutableListOf<Customer>()
    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> get() = _uiState.asStateFlow()

    init {
        _uiState.value = MainState(
            personas = emptyList(),
            personasSeleccionadas = emptyList(),
            selectMode = false
        )

        getPersonas()

        if (listaPersonas.isEmpty()) {
            getPersonasDataBase()
        }
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.GetPersonas -> {
                getPersonas()
            }

            MainEvent.ErrorVisto -> _uiState.value = _uiState.value.copy(error = null)

            is MainEvent.DeletePersonasSeleccionadas -> {
                deletePersona(uiState.value.personasSeleccionadas)
                resetSelectMode()
            }

            is MainEvent.SeleccionaPersona -> seleccionaPersona(event.customer)
            is MainEvent.GetPersonaFiltradas -> getPersonas(event.filtro)
            is MainEvent.DeletePersona -> {
                deletePersona(event.customer)
            }

            MainEvent.ResetSelectMode -> resetSelectMode()
            MainEvent.StartSelectMode -> _uiState.value = _uiState.value.copy(selectMode = true)

        }
    }

    private fun getPersonasDataBase() {
        viewModelScope.launch (Dispatchers.IO){
            roomGetCustomers.invoke().forEach { item ->

                listaPersonas.add(item.toCustomer())
            }
            _uiState.value = _uiState.value.copy(personas = listaPersonas)
        }
    }

    private fun deletePersonaRoom(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            roomDeleteCustomer.invoke(id)
        }
    }

    private fun addPersonasDataBase(personas: List<Customer>) {
        viewModelScope.launch(Dispatchers.IO) {
            roomAddCustomer.invoke(personas)
        }
    }

    private fun resetSelectMode() {
        selectedPersonas.clear()
        _uiState.value =
            _uiState.value.copy(selectMode = false, personasSeleccionadas = selectedPersonas)
    }

    private fun getPersonas() {
        viewModelScope.launch {
            when (val result = getAllCustomersUseCase.invoke()) {
                is NetworkResult.Error<*> -> _error.value = result.message ?: "error"
                is NetworkResult.Success<*> -> {
                    if (result.data is List<*>) {
                        listaPersonas.clear()
                        (result.data as List<*>).forEach { item ->
                            if (item is Customer) {
                                listaPersonas.add(item)
                            }
                        }
                    }
                }
            }
            addPersonasDataBase(listaPersonas)
            _uiState.value = _uiState.value.copy(personas = listaPersonas)
        }
    }

    private fun getPersonas(filtro: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                personas = listaPersonas.filter { it.name.startsWith(filtro) }.toList()
            )
        }
    }

    private fun deletePersona(personas: List<Customer>) {
        viewModelScope.launch {
            val copiaPersonas = personas.toList()
            val personasParaEliminar = mutableListOf<Customer>()
            var isSuccessful = true
            for (persona in copiaPersonas) {
                val result = deleteCustomerUseCase.invoke(persona)
                if (result is NetworkResult.Error<*>) {
                    _error.value = "Error al eliminar"
                    isSuccessful = false
                } else {
                    personasParaEliminar.add(persona)
                    deletePersonaRoom(persona.id)
                }
            }

            if (isSuccessful) {
                listaPersonas.removeAll(personasParaEliminar)
                selectedPersonas.removeAll(personasParaEliminar)
                _uiState.value =
                    _uiState.value.copy(personasSeleccionadas = selectedPersonas.toList())
            }

            getPersonas()
        }
    }

    private fun deletePersona(persona: Customer) {
        deletePersona(listOf(persona))
    }

    private fun seleccionaPersona(persona: Customer) {
        if (isSelected(persona)) {
            selectedPersonas.remove(persona)
        } else {
            selectedPersonas.add(persona)
        }
        _uiState.value = _uiState.value.copy(personasSeleccionadas = selectedPersonas)
    }

    private fun isSelected(persona: Customer): Boolean {
        return selectedPersonas.contains(persona)
    }
}

