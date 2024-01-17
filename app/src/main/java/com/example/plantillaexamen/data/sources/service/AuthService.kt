package com.example.plantillaexamen.data.sources.service



import com.example.plantillaexamen.data.sources.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AuthService {

        @GET(Constantes.CREDENTIALSURL)
        suspend fun refreshToken(@Query(Constantes.refreshToken) refreshToken: String?): Response<Unit>


}