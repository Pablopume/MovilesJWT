package com.example.plantillaexamen.framework.comida

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantillaexamen.domain.modelo.Comida
import com.example.plantillaexamen.domain.modelo.toComida
import com.example.plantillaexamen.domain.usecases.AddComidaUseCase
import com.example.plantillaexamen.domain.usecases.DeleteComidaUseCase
import com.example.plantillaexamen.domain.usecases.GetAllComidasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ComidaViewModel @Inject constructor(
    private val addComidaUseCase: AddComidaUseCase,
    private val getAllComidasUseCase: GetAllComidasUseCase,
    private val deleteComidaUseCase: DeleteComidaUseCase
) : ViewModel() {

    private val _listaPersonas = MutableStateFlow<List<Comida>>(emptyList())
    val listaPersonas: StateFlow<List<Comida>> get() = _listaPersonas.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    private var selectedPersonas = mutableListOf<Comida>()
    private val _uiState = MutableStateFlow(ComidaState())
    val uiState: StateFlow<ComidaState> get() = _uiState.asStateFlow()

    init {
        _uiState.value = ComidaState(
            personas = emptyList(),
            personasSeleccionadas = emptyList(),
            selectMode = false
        )

        getPersonasDataBase()
    }

    fun handleEvent(event: ComidaEvent) {
        when (event) {
            ComidaEvent.GetPersonas -> {
                // No haces nada aquí, ¿deberías cargar personas?
            }

            ComidaEvent.ErrorVisto -> _uiState.value = _uiState.value.copy(error = null)

            is ComidaEvent.DeletePersonasSeleccionadas -> {
                deletePersona(uiState.value.personasSeleccionadas)
                resetSelectMode()
            }

            is ComidaEvent.SeleccionaPersona -> seleccionaPersona(event.customer)

            is ComidaEvent.DeletePersona -> {
                deletePersona(event.customer)
            }

            ComidaEvent.ResetSelectMode -> resetSelectMode()
            ComidaEvent.StartSelectMode -> _uiState.value = _uiState.value.copy(selectMode = true)
            is ComidaEvent.AddPersona -> addPersonasDataBase(event.customer)
        }
    }

    private fun getPersonasDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            val personas = getAllComidasUseCase.invoke().map { it.toComida() }
            _listaPersonas.value = personas
            _uiState.value = _uiState.value.copy(personas = personas)
        }
    }

    private fun deletePersonaRoom(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteComidaUseCase.invoke(id)
            getPersonasDataBase()
        }
    }

    private fun addPersonasDataBase(personas: Comida) {
        viewModelScope.launch(Dispatchers.IO) {
            addComidaUseCase.invoke(personas)
            getPersonasDataBase()
        }
    }

    private fun resetSelectMode() {
        selectedPersonas.clear()
        _uiState.value =
            _uiState.value.copy(selectMode = false, personasSeleccionadas = selectedPersonas)
    }


    private fun deletePersona(personas: List<Comida>) {
        viewModelScope.launch (Dispatchers.IO){
            val copiaPersonas = personas.toList()
            val personasParaEliminar = mutableListOf<Comida>()

            for (persona in copiaPersonas) {
                deleteComidaUseCase.invoke(persona.id)
                personasParaEliminar.add(persona)
                deletePersonaRoom(persona.id)

            }



        }
    }

    private fun deletePersona(persona: Comida) {
        deletePersona(listOf(persona))
    }

    private fun seleccionaPersona(persona: Comida) {
        if (isSelected(persona)) {
            selectedPersonas.remove(persona)
        } else {
            selectedPersonas.add(persona)
        }
        _uiState.value = _uiState.value.copy(personasSeleccionadas = selectedPersonas)
    }

    private fun isSelected(persona: Comida): Boolean {
        return selectedPersonas.contains(persona)
    }
}

