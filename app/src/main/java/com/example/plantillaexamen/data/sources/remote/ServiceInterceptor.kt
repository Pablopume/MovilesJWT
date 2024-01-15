package com.example.plantillaexamen.data.sources.remote

import com.example.plantillaexamen.data.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder().build()
        val requestBuilder = originalRequest.newBuilder().url(url)
        val authorizationHeader = originalRequest.header("Authorization")
        val refreshTokenHeader = originalRequest.header("Refresh Token")
        if (authorizationHeader != null) {
            TokenManager.setAuthorizationToken(authorizationHeader)
        }
        if (refreshTokenHeader != null) {
            TokenManager.setRefreshToken(refreshTokenHeader)
        }
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}

