package com.example.restaurantserviceapp.waiting.model

sealed class WaitingSideEffect {

    data object OnNavigateToAdmin : WaitingSideEffect()

    data object OnNavigateToWaiterPage : WaitingSideEffect()
}