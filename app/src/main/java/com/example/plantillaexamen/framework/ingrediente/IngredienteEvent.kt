package com.example.plantillaexamen.framework.ingrediente
import com.example.plantillaexamen.domain.modelo.Ingrediente

sealed class IngredienteEvent {

    data object DeletePersonasSeleccionadas : IngredienteEvent()
    class DeletePersona(val customer: Ingrediente) : IngredienteEvent()
    class SeleccionaPersona(val customer: Ingrediente) : IngredienteEvent()
    class AddPersona(val customer: Ingrediente) : IngredienteEvent()
    class  GetPersonas(val id: Int) : IngredienteEvent()
    data object ErrorVisto : IngredienteEvent()
    data object StartSelectMode: IngredienteEvent()
    data object ResetSelectMode: IngredienteEvent()

}
