package com.example.plantillaexamen.framework.ingrediente


import IngredienteAdapter
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.plantillaexamen.databinding.FragmentIngredienteBinding
import com.example.plantillaexamen.domain.modelo.Ingrediente
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IngredienteFragment : Fragment() {
    private var _binding: FragmentIngredienteBinding? = null
    private var primeraVez: Boolean = false
    val args: IngredienteFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private lateinit var comidaAdapter: IngredienteAdapter
    private val viewModel: IngredienteViewModel by viewModels()
    private var actionMode: ActionMode? = null
    private val callback by lazy {
        configContextBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comidaAdapter = IngredienteAdapter(requireContext(),
            object : IngredienteAdapter.PersonaActions {
                override fun onDelete(customer: Ingrediente) =
                    viewModel.handleEvent(IngredienteEvent.DeletePersona(customer))

                override fun onStartSelectMode(customer: Ingrediente) {
                    viewModel.handleEvent(IngredienteEvent.StartSelectMode)
                    viewModel.handleEvent(IngredienteEvent.SeleccionaPersona(customer))
                }

                override fun itemHasClicked(customer: Ingrediente) {
                    viewModel.handleEvent(IngredienteEvent.SeleccionaPersona(customer))
                }

            })
        initRecyclerView()
        initViewModel()
        viewModel.handleEvent(IngredienteEvent.GetPersonas(args.idcustomer))
        val id: Int = args.idcustomer
        binding.btnAdd.setOnClickListener {
            viewModel.handleEvent(

                IngredienteEvent.AddPersona(
                    Ingrediente(
                        (1..6000).random(),
                        "Chili",
                        id, false
                    )
                )
            )
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


    private fun handlePersonaState(state: IngredienteState) {
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

    private fun handleSelectModeState(state: IngredienteState) {
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

    private fun handleViewState(state: IngredienteState) {
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
            viewModel.handleEvent(IngredienteEvent.ResetSelectMode)
        }
    }

}