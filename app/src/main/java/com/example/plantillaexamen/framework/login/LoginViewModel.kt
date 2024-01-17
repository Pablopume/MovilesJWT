package com.example.plantillaexamen.framework.login


import androidx.lifecycle.ViewModel
import com.example.plantillaexamen.domain.modelo.Credentials
import com.example.plantillaexamen.domain.usecases.ForgotUseCase
import com.example.plantillaexamen.domain.usecases.LoginUseCase
import com.example.plantillaexamen.domain.usecases.RegisterUseCase
import com.example.plantillaexamen.framework.ConstantesFramework
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import javax.inject.Inject



@HiltViewModel
class LoginViewModel @Inject constructor(private val forgotUseCase: ForgotUseCase,private val loginUseCase: LoginUseCase, private val registerUseCase: RegisterUseCase): ViewModel() {
    private val _state = MutableStateFlow(LoginState(error = null, login = false, register = false))
    val state: StateFlow<LoginState> get() = _state

    suspend fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login(event.user, event.password)
            }
            is LoginEvent.Register -> {
                     register(event.user, event.password)

            }
            is LoginEvent.ForgotPassword -> {
                forgotPassword(event.email)
            }
        }
    }

    private suspend fun forgotPassword(email: String) {
        when (val result = forgotUseCase(email)) {
            is NetworkResult.Success -> {
                _state.value = _state.value.copy(error = null, forgotPassword = true)
            }
            is NetworkResult.Error -> {
                _state.value = _state.value.copy(error = result.message ?: ConstantesFramework.ERROR)
            }
        }
    }

    private suspend fun register(user: String, password: String) {
val credentials= Credentials(user,password,false, LocalDateTime.now(),
    ConstantesFramework.EMPTY, ConstantesFramework.USER,
    ConstantesFramework.EMPTY,
    ConstantesFramework.EMPTY,
    ConstantesFramework.EMPTY
)

when (val result = registerUseCase(credentials)) {
            is NetworkResult.Success -> {
                _state.value = _state.value.copy(error = null, register = true)
            }
            is NetworkResult.Error -> {
                _state.value = _state.value.copy(error = result.message ?: ConstantesFramework.ERROR)
            }
        }
    }

    private suspend fun login(user: String, password: String) {
        when (val result = loginUseCase(user, password)) {
            is NetworkResult.Success -> {
                _state.value = _state.value.copy(error = null, login = true)
            }
            is NetworkResult.Error -> {
                _state.value = _state.value.copy(error = result.message ?: ConstantesFramework.ERROR)
            }
        }
    }
}