package com.example.plantillaexamen.domain.modelo

import com.example.plantillaexamen.data.model.CredentialsResponse
import java.time.LocalDateTime


data class Credentials (
    val email: String ,
    val password: String ,
     val activado : Boolean,
     val fechaActivacion: LocalDateTime,
     val codigoActivacion: String,
     val rol: String,
     val accessToken: String,
     val refreshToken: String,
     val temporalPassword: String,
)
fun Credentials.toCrendetialsResponse() : CredentialsResponse = CredentialsResponse(email, password, activado, fechaActivacion.toString(), codigoActivacion, rol, accessToken, refreshToken, temporalPassword)