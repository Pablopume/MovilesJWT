package com.example.plantillaexamen.framework.ingrediente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantillaexamen.domain.modelo.Ingrediente
import com.example.plantillaexamen.domain.modelo.toIngrediente
import com.example.plantillaexamen.domain.usecases.AddIngredienteUseCase
import com.example.plantillaexamen.domain.usecases.DeleteIngredienteUseCase
import com.example.plantillaexamen.domain.usecases.GetIngredientesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredienteViewModel @Inject constructor(
    private val addComidaUseCase: AddIngredienteUseCase,
    private val getAllComidasUseCase: GetIngredientesUseCase,
    private val deleteComidaUseCase: DeleteIngredienteUseCase
) : ViewModel() {

    private val _listaPersonas = MutableStateFlow<List<Ingrediente>>(emptyList())


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    private var selectedPersonas = mutableListOf<Ingrediente>()
    private val _uiState = MutableStateFlow(IngredienteState())
    val uiState: StateFlow<IngredienteState> get() = _uiState.asStateFlow()

    init {
        _uiState.value = IngredienteState(
            personas = emptyList(),
            personasSeleccionadas = emptyList(),
            selectMode = false
        )


    }

    fun handleEvent(event: IngredienteEvent) {
        when (event) {
            is IngredienteEvent.GetPersonas -> {
                getPersonasDataBase(event.id)
            }

            IngredienteEvent.ErrorVisto -> _uiState.value = _uiState.value.copy(error = null)

            is IngredienteEvent.DeletePersonasSeleccionadas -> {
                deletePersona(uiState.value.personasSeleccionadas)
                resetSelectMode()
            }

            is IngredienteEvent.SeleccionaPersona -> seleccionaPersona(event.customer)

            is IngredienteEvent.DeletePersona -> {
                deletePersona(event.customer)
                getPersonasDataBase(event.customer.comidaId)
            }

            IngredienteEvent.ResetSelectMode -> resetSelectMode()
            IngredienteEvent.StartSelectMode -> _uiState.value =
                _uiState.value.copy(selectMode = true)

            is IngredienteEvent.AddPersona -> addPersonasDataBase(event.customer)

        }
    }

    private fun getPersonasDataBase(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val personas = getAllComidasUseCase.invoke(id).map { it.toIngrediente() }
            _listaPersonas.value = personas
            _uiState.value = _uiState.value.copy(personas = personas)
        }
    }

    private fun deletePersonaRoom(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteComidaUseCase.invoke(id)
            // Después de eliminar, actualiza la lista y el estado

        }
    }

    private fun addPersonasDataBase(personas: Ingrediente) {
        viewModelScope.launch(Dispatchers.IO) {
            addComidaUseCase.invoke(personas)
            // Después de agregar, actualiza la lista y el estado
            getPersonasDataBase(personas.comidaId)
        }
    }

    private fun resetSelectMode() {
        selectedPersonas.clear()
        _uiState.value =
            _uiState.value.copy(selectMode = false, personasSeleccionadas = selectedPersonas)
    }


    private fun deletePersona(personas: List<Ingrediente>) {
        viewModelScope.launch(Dispatchers.IO) {
            val copiaPersonas = personas.toList()
            val personasParaEliminar = mutableListOf<Ingrediente>()

            for (persona in copiaPersonas) {
                deleteComidaUseCase.invoke(persona.id)
                personasParaEliminar.add(persona)
                deletePersonaRoom(persona.id)

            }


        }
    }

    private fun deletePersona(persona: Ingrediente) {
        deletePersona(listOf(persona))
    }

    private fun seleccionaPersona(persona: Ingrediente) {
        if (isSelected(persona)) {
            selectedPersonas.remove(persona)
        } else {
            selectedPersonas.add(persona)
        }
        _uiState.value = _uiState.value.copy(personasSeleccionadas = selectedPersonas)
    }

    private fun isSelected(persona: Ingrediente): Boolean {
        return selectedPersonas.contains(persona)
    }
}

