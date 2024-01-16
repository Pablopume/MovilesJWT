package com.example.plantillaexamen.data.sources.remote

import com.example.plantillaexamen.data.TokenManager
import com.example.plantillaexamen.data.sources.service.AuthService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TokenAuthenticator @Inject constructor () : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            val newToken = runBlocking {
                getNewToken(TokenManager.getRefreshToken())
            }
            return response.request.newBuilder()
                .header("Authorization", "$newToken")
                .build()
        }
        return null
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<Unit> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthService::class.java)
        return service.refreshToken("$refreshToken")
    }
}
