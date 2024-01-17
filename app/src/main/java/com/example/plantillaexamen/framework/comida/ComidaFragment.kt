package com.example.plantillaexamen.framework.comida


import ComidaAdapter
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
import com.example.plantillaexamen.databinding.FragmentComidaBinding
import com.example.plantillaexamen.domain.modelo.Comida
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComidaFragment : Fragment() {
    private var _binding: FragmentComidaBinding? = null
    private var primeraVez: Boolean = false
    private val binding get() = _binding!!
    private lateinit var comidaAdapter: ComidaAdapter
    private val viewModel: ComidaViewModel by viewModels()
    private var actionMode: ActionMode? = null
    private val callback by lazy {
        configContextBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comidaAdapter = ComidaAdapter(requireContext(),
            object : ComidaAdapter.PersonaActions {
                override fun onDelete(customer: Comida) =
                    viewModel.handleEvent(ComidaEvent.DeletePersona(customer))

                override fun onStartSelectMode(customer: Comida) {
                    viewModel.handleEvent(ComidaEvent.StartSelectMode)
                    viewModel.handleEvent(ComidaEvent.SeleccionaPersona(customer))
                }

                override fun itemHasClicked(customer: Comida) {
                    viewModel.handleEvent(ComidaEvent.SeleccionaPersona(customer))
                }

                override fun onClickItem(customerId: Int) {
                    click(customerId)
                }

            })
        initRecyclerView()
        initViewModel()

        binding.btnAdd.setOnClickListener {
            viewModel.handleEvent(
                ComidaEvent.AddPersona(
                    Comida(
                        (1..6000).random(),
                        "Pasta",
                        false
                    )
                )
            )
        }
    }


    private fun click(customerId: Int) {
        if (!viewModel.uiState.value.selectMode) {
            val action = ComidaFragmentDirections.actionSegundoFragmentToTercerFragment(customerId)
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {
        binding.rvPersonas.adapter = comidaAdapter
        val touchHelper = ItemTouchHelper(comidaAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvPersonas)
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handlePersonaState(state)
                handleSelectModeState(state)
                handleViewState(state)
                comidaAdapter.submitList(state.personas)

            }
        }
    }




    private fun handlePersonaState(state: ComidaState) {
        state.personas.let {
            if (it.isNotEmpty()) {
                comidaAdapter.submitList(it)
            }
        }

        state.personasSeleccionadas.let {
            if (it.isNotEmpty()) {
                comidaAdapter.setSelectedItems(it)
            } else {
                comidaAdapter.resetSelectMode()
                primeraVez = true
                actionMode?.finish()
            }
        }
    }

    private fun handleSelectModeState(state: ComidaState) {
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
            comidaAdapter.startSelectMode()
            (activity as? AppCompatActivity)?.startSupportActionMode(callback)?.let {
                actionMode = it
            }
            primeraVez = false
        } else {
            comidaAdapter.startSelectMode()
        }
    }


    private fun handleSelectModeFalse() {
        comidaAdapter.resetSelectMode()
        primeraVez = true
        actionMode?.finish()
    }

    private fun handleViewState(state: ComidaState) {
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

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            viewModel.handleEvent(ComidaEvent.ResetSelectMode)
        }
    }

}