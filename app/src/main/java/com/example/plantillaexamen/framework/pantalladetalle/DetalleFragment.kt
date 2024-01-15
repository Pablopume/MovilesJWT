package com.example.plantillaexamen.framework.pantalladetalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.plantillaexamen.databinding.FragmentDetalleBinding


import com.example.restaurantapi.domain.modelo.Order
import com.example.restaurantapi.framework.pantallarorders.OrderEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleFragment : Fragment() {
    val args: DetalleFragmentArgs by navArgs()
    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!
    private var primeraVez: Boolean = false
    private var actionMode: ActionMode? = null
    private lateinit var customAdapter: OrderAdapter
    private val viewModel: OrdersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: Int = args.idcustomer
        initRecyclerView()
        initViewModel()
        handleIntent(id)
        getCustomer(id)
        add(id)
    }

    private fun add(id: Int) {
        with(binding) {
            addOrder.setOnClickListener {
                val action = DetalleFragmentDirections.actionDetalleFragmentToAddFragment(id)
                findNavController().navigate(action)
            }
        }
    }



    private fun getCustomer(id: Int) {
        lifecycleScope.launch {

            viewModel.handleEvent(OrderEvent.GetCustomer(id))

        }    }

    private fun handleIntent(id: Int) {
        lifecycleScope.launch {

            viewModel.handleEvent(OrderEvent.GetOrders(id))

        }    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handlePersonaState(state)
                handleViewState(state)
                handleCustomerActual(state)
            }
        }
    }


    private fun handleCustomerActual(state: OrdersState) {
        state.customerActual.let {
            if (it != null) {

            }
        }
    }


    private fun handleViewState(state: OrdersState) {
        state.error?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handlePersonaState(state: OrdersState) {
        state.personas.let {
            if (it.isNotEmpty()) {
                customAdapter.submitList(it)
            }
        }
        state.personasSeleccionadas.let {
            if (it.isNotEmpty()) {
                customAdapter.setSelectedItems(it)
            } else {
                customAdapter.resetSelectMode()
                primeraVez = true
                actionMode?.finish()
            }
        }
    }

    private fun initRecyclerView() {
        customAdapter = OrderAdapter(
            requireContext(), createPersonaActions()
        )
        binding.rvPersonas.adapter = customAdapter
        val touchHelper = ItemTouchHelper(customAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvPersonas)
    }

    private fun createPersonaActions(): (Order) -> Unit {
        return { order ->
            lifecycleScope.launch {
                viewModel.handleEvent(OrderEvent.DeletePersona(order))
            }
        }
    }
}
