package com.example.plantillaexamen.framework.pantallamain


import com.example.plantillaexamen.domain.modelo.Customer

sealed class MainEvent {

    data object DeletePersonasSeleccionadas : MainEvent()
    class DeletePersona(val customer: Customer) : MainEvent()
    class SeleccionaPersona(val customer: Customer) : MainEvent()
    class GetPersonaFiltradas(val filtro: String) : MainEvent()
    data object GetPersonas : MainEvent()
    data object ErrorVisto : MainEvent()
    data object StartSelectMode: MainEvent()
    data object ResetSelectMode: MainEvent()

}
