package com.example.restaurantserviceapp.singup.model

sealed class SignUpSideEffect {

    data object OnShowError : SignUpSideEffect()

    data object OnNavigateToWaitScreen : SignUpSideEffect()

}