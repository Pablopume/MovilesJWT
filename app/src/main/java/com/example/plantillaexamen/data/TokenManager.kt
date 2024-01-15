package com.example.plantillaexamen.data

object TokenManager {
    private var authorizationToken: String? = null
    private var refreshToken: String? = null

    fun setAuthorizationToken(token: String?) {
        authorizationToken = token
    }
    fun setRefreshToken(token: String?) {
        refreshToken = token
    }

    fun getAuthorizationToken(): String? {
        return authorizationToken
    }

    fun getRefreshToken(): String? {
        return refreshToken
    }
}
