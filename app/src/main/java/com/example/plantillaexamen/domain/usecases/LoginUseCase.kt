package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.CredentialRepsitory
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val credentialRepsitory: CredentialRepsitory) {
    suspend operator fun invoke(email: String, password: String) = credentialRepsitory.login(email, password)
}