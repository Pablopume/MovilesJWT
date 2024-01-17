package com.example.plantillaexamen.framework.login


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.plantillaexamen.R
import com.example.plantillaexamen.databinding.LoginBinding
import com.example.plantillaexamen.framework.MainActivityBottom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(), MenuProvider {
    val args: LoginFragmentArgs by navArgs()
    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.viewModelScope.launch {


            viewModel.state.collect { state ->

                if (state.error != null) {

                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }
                if (state.login) {
                    val intent = Intent(requireContext(), MainActivityBottom::class.java).apply {

                    }
                    startActivity(intent)
                }
                if (state.register) {
                    Toast.makeText(
                        requireContext(),
                        "Te hemos mandado un correo",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                if (state.forgotPassword) {
                    Toast.makeText(
                        requireContext(),
                        "Te hemos mandado un correo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        with(binding)
        {
            btnLogin.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.handleEvent(
                        LoginEvent.Login(
                            binding.editTextUsername.text.toString(),
                            binding.editTextPassword.text.toString()
                        )
                    )

                }
            }
            btnRegister.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.handleEvent(
                        LoginEvent.Register(
                            binding.editTextUsername.text.toString(),
                            binding.editTextPassword.text.toString()
                        )
                    )
                }
            }
            btnForgotPassword.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.handleEvent(
                        LoginEvent.ForgotPassword(
                            binding.editTextUsername.text.toString()
                        )
                    )
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        //NOP
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
        R.id.segundoFragment -> {
            val action =
                LoginFragmentDirections.actionLoginFragmentToSegundoFragment(1)
            findNavController().navigate(action)
            true
        }

        else -> false
    }
    }
}