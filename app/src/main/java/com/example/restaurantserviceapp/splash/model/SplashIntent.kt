package com.example.restaurantserviceapp.splash.model

sealed class SplashIntent {

    data object OnViewCreated : SplashIntent()
}