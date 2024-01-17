package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.CredentialRepsitory
import com.example.plantillaexamen.domain.modelo.Credentials
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private  val credentialRepsitory: CredentialRepsitory) {
    suspend operator fun invoke(credentials: Credentials) = credentialRepsitory.register(credentials)

}