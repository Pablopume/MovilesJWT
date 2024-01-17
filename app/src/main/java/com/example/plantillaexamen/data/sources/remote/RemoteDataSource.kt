package com.example.plantillaexamen.data.sources.remote

import com.example.plantillaexamen.data.TokenManager
import com.example.plantillaexamen.data.model.toCustomer
import com.example.plantillaexamen.data.model.toOrder
import com.example.plantillaexamen.data.sources.Constantes
import com.example.plantillaexamen.data.sources.service.AuthService
import com.example.plantillaexamen.data.sources.service.CredentialsService
import com.example.plantillaexamen.data.sources.service.CustomerService
import com.example.plantillaexamen.data.sources.service.OrderService
import com.example.plantillaexamen.domain.modelo.Credentials
import com.example.plantillaexamen.domain.modelo.Customer
import com.example.plantillaexamen.domain.modelo.toCrendetialsResponse
import com.example.plantillaexamen.utils.NetworkResult
import com.example.restaurantapi.domain.modelo.Order
import com.example.restaurantapi.domain.modelo.toOrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject



class RemoteDataSource @Inject constructor(
    private val orderService: OrderService,
    private val customerService: CustomerService,
    private val authService: AuthService,
    private val credentialsService: CredentialsService,
    private var tokenManager: TokenManager
) {

    suspend fun login(email: String, password: String): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val call = credentialsService.getLogin(email, password)
                val response = call.execute()
                if (response.isSuccessful) {

                    val accessToken = response.headers()[Constantes.AUTHORIZATION]
                    val refreshToken = response.headers()[Constantes.REFRESH_TOKEN]

                    // Guarda los tokens en tokenManager
                    if (accessToken != null) {
                        tokenManager.saveAccessToken(accessToken)
                    }
                    if (refreshToken != null) {
                        tokenManager.saveToken(refreshToken)
                    }

                    NetworkResult.Success(Constantes.DONE)
                } else {
                    NetworkResult.Error("${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: e.toString())
            }
        }
    }

    suspend fun register(credentials: Credentials): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = credentialsService.addCredentials(credentials.toCrendetialsResponse())
                val call=response.execute()
                if (call.isSuccessful) {
                    NetworkResult.Success(Constantes.DONE)
                } else {
                    NetworkResult.Error("${call.code()} ${call.message()}")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: e.toString())
            }
        }
    }
    suspend fun forgotPassword(email: String): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val call = credentialsService.forgotPassword(email)
                val response = call.execute()
                if (response.isSuccessful) {
                    NetworkResult.Success(Constantes.DONE)
                } else {
                    NetworkResult.Error("${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: e.toString())
            }
        }
    }
    suspend fun getCustomers(): NetworkResult<List<Customer>> {

        try  {
            val response = customerService.getCustomers()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.map { it.toCustomer() })
                }
            } else {
                return NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
        return NetworkResult.Error(Constantes.ERROR)
    }



    suspend fun deleteCustomer(id: Int): NetworkResult<Unit> {
        return try {
            val response = customerService.deleteCustomer(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }



    suspend fun getCustomer(id: Int): NetworkResult<Customer> {
        try {
            val response = customerService.getCustomer(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(it.toCustomer())
                }
            } else {
                return NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
        return NetworkResult.Error(Constantes.ERROR)
    }

    suspend fun refreshToken(refreshToken: String?): NetworkResult<String> {
        try {
            val response = authService.refreshToken(refreshToken)

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(Constantes.DONE) // Asegúrate de ajustar el tipo según la respuesta de tu API
                }
            } else {
                return NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
        return NetworkResult.Error(Constantes.ERROR)
    }

    suspend fun getOrders(): NetworkResult<List<Order>> {
        try {
            val response = orderService.getOrders()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.map { it.toOrder() })
                }
            } else {
                return NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
        return NetworkResult.Error(Constantes.ERROR)
    }

    suspend fun deleteOrder(id: Int): NetworkResult<Unit> {
        return try {
            val response = orderService.deleteOrder(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun createOrder(order: Order): NetworkResult<Order> {
        try {
            val response = orderService.createOrder(order.toOrderResponse())
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(it.toOrder())
                }
            } else {
                return NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
        return NetworkResult.Error(Constantes.ERROR)
    }


}
