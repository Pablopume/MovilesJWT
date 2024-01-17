package com.example.plantillaexamen.framework.login

sealed class LoginEvent {
class Login(val user: String, val password: String) : LoginEvent()
class Register(val user: String, val password: String) : LoginEvent()
class ForgotPassword(val email: String) : LoginEvent()

}