package com.example.plantillaexamen.data

import com.example.plantillaexamen.data.sources.remote.RemoteDataSource
import com.example.plantillaexamen.domain.modelo.Credentials
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class CredentialRepsitory @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun login(email: String, password: String) = remoteDataSource.login(email, password)

    suspend fun register(credentials: Credentials) = remoteDataSource.register(credentials)
    suspend fun forgotPassword(email: String) = remoteDataSource.forgotPassword(email)
}