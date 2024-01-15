package com.example.plantillaexamen.framework.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.plantillaexamen.databinding.AddFragmentBinding


import com.example.plantillaexamen.databinding.FragmentMainBinding
import com.example.plantillaexamen.framework.pantalladetalle.OrdersViewModel
import com.example.restaurantapi.domain.modelo.Order
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class AddFragment: Fragment() {
    val args: AddFragmentArgs by navArgs()
    private var _binding: AddFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id: Int = args.idcustomer
        with(binding) {
            button.setOnClickListener {
                val order = Order(0, id, LocalDate.now(), textName.text.toString().toInt())

                lifecycleScope.launch {
                    viewModel.handleEvent(AddOrderEvent.AddOrder(order))
                    val action = AddFragmentDirections.actionAddFragmentToDetalleFragment(id)
                    findNavController().navigate(action)
                }
            }
        }
    }
}
