package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.sources.service.AuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor (private val authService: AuthService) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            val newToken = authService.refreshToken(TokenManager.getRefreshToken())

            return response.request.newBuilder()
                .header("Authorization", "$newToken")
                .build()
        }
        // Si no se puede refrescar el token o el nuevo token es nulo, devuelve nulo para indicar que no se puede autenticar.
        return null
    }
}
