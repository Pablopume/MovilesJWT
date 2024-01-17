package com.example.plantillaexamen.data.sources.service

import com.example.plantillaexamen.data.model.OrderResponse
import com.example.plantillaexamen.data.sources.Constantes
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface OrderService {
    @GET(Constantes.ORDERURL)
    suspend fun getOrders(): Response<List<OrderResponse>>

    @DELETE(Constantes.ORDERURL2)
    suspend fun deleteOrder(@Path(Constantes.id) id: Int): Response<ResponseBody>

    @POST(Constantes.ORDERURL)
    suspend fun createOrder(@Body order: OrderResponse): Response<OrderResponse>


}