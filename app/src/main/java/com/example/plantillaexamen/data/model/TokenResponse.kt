package com.example.plantillaexamen.data.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("accessToken") val accessToken: String?,
    // Otros campos relevantes
)