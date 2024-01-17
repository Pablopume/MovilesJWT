package com.example.plantillaexamen.framework.pantalladetalle

import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.framework.ConstantesFramework
import com.example.restaurantapi.domain.modelo.Order
import java.time.LocalDate



data class OrdersState (val personas: List<Order> = emptyList(),
                        val personasSeleccionadas: List<Order> = emptyList(),
                        val error: String? = null,
                        val customerActual: Customer? = Customer(0,
                            ConstantesFramework.EMPTY,
                            ConstantesFramework.EMPTY,
                            ConstantesFramework.EMPTY,
                            ConstantesFramework.EMPTY, LocalDate.now(),isSelected = false)  )