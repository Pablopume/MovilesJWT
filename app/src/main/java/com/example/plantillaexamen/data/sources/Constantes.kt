package com.example.plantillaexamen.data.sources

object Constantes {
    const val baseurl = "http://192.168.1.155:8080/PSP_JWT-1.0-SNAPSHOT/api/"
    const val datastore = "data_store"
    const val email = "email"
    const val password = "password"
    const val activado = "activado"
    const val fechaActivacion = "fechaActivacion"
    const val codigoActivacion = "codigoActivacion"
    const val rol = "rol"
    const val accessToken = "accessToken"
    const val refreshToken = "refreshToken"
    const val temporalPassword = "temporalPassword"
    const val id = "id"
    const val name = "name"
    const val lastName = "lastName"
    const val phone = "phone"
    const val dob = "birthDate"
    const val customerId = "customerId"
    const val orderDate = "orderDate"
    const val tableId = "tableId"
    const val APP_DB = "app.db"
    const val AUTHORIZATION = "Authorization"
    const val REFRESH_TOKEN = "Refresh token"
    const val DONE = "Done"
    const val ERROR = "Something went wrong."
    const val REFRESH = "refresh_token"
    const val CREDENTIALSURL = "credentials/refreshToken"
    const val ACCESS_TOKEN = "access_token"
    const val QUERY = "SELECT * FROM comida_table"
    const val Query2 = "DELETE FROM comida_table WHERE id = :id"
    const val CREDENTIALSURL2 = "credentials"
    const val CREDENTIALSLOGIN = "credentials/login"
    const val CREDENTIALSFORGOTPASSWORD = "credentials/forgot-password"
    const val USER = "user"
    const val PASSWORD = "password"
    const val EMAIL = "email"
    const val QUERY3 = "SELECT * FROM customer_table"
    const val QUERY4 = "DELETE FROM customer_table WHERE id = :id"
    const val CUSTOMERURL = "customer"
    const val CUSTOMERURL2 = "customer/{id}"
    const val QUERY5 = "SELECT * FROM ingrediente_table where comidaId = :id"
    const val QUERY6 = "DELETE FROM ingrediente_table WHERE id = :id"
    const val ORDERURL = "order"
    const val ORDERURL2 = "order/{id}"
}