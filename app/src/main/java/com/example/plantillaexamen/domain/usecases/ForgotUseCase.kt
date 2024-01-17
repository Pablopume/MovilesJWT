package com.example.plantillaexamen.domain.usecases

import com.example.plantillaexamen.data.CredentialRepsitory
import javax.inject.Inject

class ForgotUseCase @Inject constructor(private val credentialRepsitory: CredentialRepsitory) {

    suspend operator fun invoke(email: String) = credentialRepsitory.forgotPassword(email)
}