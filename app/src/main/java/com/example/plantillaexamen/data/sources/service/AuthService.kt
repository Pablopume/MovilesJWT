package com.example.plantillaexamen.data.sources.service


import com.example.plantillaexamen.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {



        @GET("credentials/refreshToken")
        suspend fun refreshToken(@Query("refreshToken") refreshToken: String?): Response<Unit>


}