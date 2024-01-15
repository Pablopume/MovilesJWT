package com.example.plantillaexamen.data.sources.service


import com.example.plantillaexamen.data.model.CustomerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface CustomerService {

    @GET("customer")
    suspend fun getCustomers(@Header("Authorization") accessToken: String?): Response<List<CustomerResponse>>

    @DELETE("customer/{id}")
    suspend fun deleteCustomer(@Header("Authorization") accessToken: String?, @Path("id") id: Int): Response<ResponseBody>

    @GET("customer/{id}")
    suspend fun getCustomer(@Header("Authorization") accessToken: String?, @Path("id") id: Int): Response<CustomerResponse>


}