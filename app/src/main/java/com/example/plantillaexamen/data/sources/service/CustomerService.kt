package com.example.plantillaexamen.data.sources.service


import com.example.plantillaexamen.data.model.CustomerResponse
import com.example.plantillaexamen.data.sources.Constantes
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface CustomerService {

    @GET(Constantes.CUSTOMERURL)
    suspend fun getCustomers(): Response<List<CustomerResponse>>

    @DELETE(Constantes.CUSTOMERURL2)
    suspend fun deleteCustomer(@Path(Constantes.id) id: Int): Response<ResponseBody>

    @GET(Constantes.CUSTOMERURL2)
    suspend fun getCustomer( @Path(Constantes.id) id: Int): Response<CustomerResponse>


}