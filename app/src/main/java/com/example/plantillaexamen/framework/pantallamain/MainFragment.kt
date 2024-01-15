package com.example.plantillaexamen.framework.pantallamain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.plantillaexamen.databinding.FragmentMainBinding
import com.example.plantillaexamen.domain.modelo.Customer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private var primeraVez: Boolean = false
    private val binding get() = _binding!!
    private lateinit var customersAdapter: CustomerAdapter
    private val viewModel: MainViewModel by viewModels()
    private var actionMode: ActionMode? = null
    private val callback by lazy {
        configContextBar()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customersAdapter = CustomerAdapter(requireContext(),
            object : CustomerAdapter.PersonaActions {
                override fun onDelete(customer: Customer) =
                    viewModel.handleEvent(MainEvent.DeletePersona(customer))

                override fun onStartSelectMode(customer: Customer) {
                    viewModel.handleEvent(MainEvent.StartSelectMode)
                    viewModel.handleEvent(MainEvent.SeleccionaPersona(customer))
                }

                override fun itemHasClicked(customer: Customer) {
                    viewModel.handleEvent(MainEvent.SeleccionaPersona(customer))
                }

                override fun onClickItem(customerId: Int) {
                    click(customerId)
                }

            })
        initRecyclerView()
        initViewModel()
    }


    private fun click(customerId: Int) {
        if (!viewModel.uiState.value.selectMode) {
            val action = MainFragmentDirections.actionPrimerFragmentToDetalleFragment(customerId)
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {
        binding.rvPersonas.adapter = customersAdapter
        val touchHelper = ItemTouchHelper(customersAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvPersonas)
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handlePersonaState(state)
                handleSelectModeState(state)
                handleViewState(state)
            }
        }
    }


    private fun handlePersonaState(state: MainState) {
        state.personas.let {
            if (it.isNotEmpty()) {
                customersAdapter.submitList(it)
            }
        }

        state.personasSeleccionadas.let {
            if (it.isNotEmpty()) {
                customersAdapter.setSelectedItems(it)
            } else {
                customersAdapter.resetSelectMode()
                primeraVez = true
                actionMode?.finish()
            }
        }
    }

    private fun handleSelectModeState(state: MainState) {
        state.selectMode.let { seleccionado ->
            if (seleccionado) {
                handleSelectModeTrue()
            } else {
                handleSelectModeFalse()
            }
        }
    }

    private fun handleSelectModeTrue() {
        if (primeraVez) {
            customersAdapter.startSelectMode()
            (activity as? AppCompatActivity)?.startSupportActionMode(callback)?.let {
                actionMode = it
            }
            primeraVez = false
        } else {
            customersAdapter.startSelectMode()
        }
    }




    private fun handleSelectModeFalse() {
        customersAdapter.resetSelectMode()
        primeraVez = true
        actionMode?.finish()
    }

    private fun handleViewState(state: MainState) {
        state.error?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        }
    }

    private fun configContextBar() = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode:ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode:ActionMode?) {
            viewModel.handleEvent(MainEvent.ResetSelectMode)
        }
    }

}
