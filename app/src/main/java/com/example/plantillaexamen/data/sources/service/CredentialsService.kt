package com.example.plantillaexamen.data.sources.service

import com.example.plantillaexamen.data.model.CredentialsResponse
import com.example.plantillaexamen.data.sources.Constantes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CredentialsService {

    @GET(Constantes.CREDENTIALSLOGIN)
    fun getLogin(@Query(Constantes.USER) user: String, @Query(Constantes.PASSWORD) password: String): Call<Void>

    @POST(Constantes.CREDENTIALSURL2)
    fun addCredentials(@Body credential: CredentialsResponse): Call<Response<CredentialsResponse>>

    @GET(Constantes.CREDENTIALSFORGOTPASSWORD)
    fun forgotPassword(@Query(Constantes.EMAIL) email: String): Call<Void>
}