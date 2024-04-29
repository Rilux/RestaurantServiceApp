package com.example.restaurantserviceapp.login.ui.model

sealed class LoginIntent {

    data class OnCredentialsEntered(
        val email: String,
        val password: String,
    ) : LoginIntent()
}