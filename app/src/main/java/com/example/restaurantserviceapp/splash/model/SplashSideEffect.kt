package com.example.restaurantserviceapp.splash.model

sealed class SplashSideEffect {

    data object ShowLogin: SplashSideEffect()

    data object ShowAdminContent: SplashSideEffect()

    data object ShowServerContent: SplashSideEffect()

}
