package com.example.plantillaexamen.data.model

import com.example.plantillaexamen.data.sources.Constantes
import com.google.gson.annotations.SerializedName


data class CredentialsResponse(

    @SerializedName(Constantes.email)
    val email: String,
    @SerializedName(Constantes.password)
    val password: String,
    @SerializedName(Constantes.activado)
    val activado: Boolean,
    @SerializedName(Constantes.fechaActivacion)
    val fechaActivacion: String,
    @SerializedName(Constantes.codigoActivacion)
    val codigoActivacion: String,
    @SerializedName(Constantes.rol)
    val rol: String,
    @SerializedName(Constantes.accessToken)
    val accessToken: String,
    @SerializedName(Constantes.refreshToken)
    val refreshToken: String,
    @SerializedName(Constantes.temporalPassword)
    val temporalPassword: String
)
