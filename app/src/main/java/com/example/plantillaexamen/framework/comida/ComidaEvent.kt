package com.example.plantillaexamen.framework.comida


import com.example.plantillaexamen.domain.modelo.Comida

sealed class ComidaEvent {

    data object DeletePersonasSeleccionadas : ComidaEvent()
    class DeletePersona(val customer: Comida) : ComidaEvent()
    class SeleccionaPersona(val customer: Comida) : ComidaEvent()
    class AddPersona(val customer: Comida) : ComidaEvent()
    data object GetPersonas : ComidaEvent()
    data object ErrorVisto : ComidaEvent()
    data object StartSelectMode: ComidaEvent()
    data object ResetSelectMode: ComidaEvent()

}
