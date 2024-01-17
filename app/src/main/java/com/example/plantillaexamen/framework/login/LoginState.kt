package com.example.plantillaexamen.framework.login

data class LoginState (
    val error: String? = null,
    val login: Boolean = false,
    val register: Boolean = false,
    val forgotPassword: Boolean = false
)