package com.example.restaurantserviceapp.singup.model

sealed class SignUpIntent {

    data class OnCreateUser(
        val userName: String,
        val email: String,
        val password: String,
        val repeatedPassword: String,
    ) : SignUpIntent()
}