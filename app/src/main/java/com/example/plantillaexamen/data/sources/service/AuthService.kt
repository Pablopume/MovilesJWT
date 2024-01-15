package com.example.plantillaexamen.data.sources.service

import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {

        @GET("credentials/refreshToken")
        fun refreshToken(@Query("refreshToken") refreshToken: String?)


}