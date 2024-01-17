package com.example.plantillaexamen.data.sources.remote

import com.example.plantillaexamen.data.TokenManager
import com.example.plantillaexamen.data.sources.Constantes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ServiceInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
      val token = runBlocking {
          tokenManager.getAccessToken().first()

      }
        val request = chain.request().newBuilder().header(Constantes.AUTHORIZATION, "$token").build()
        return chain.proceed(request)
    }
}

