package com.example.restaurantserviceapp.waiting.model

sealed class WaitingIntent {

    data object OnViewCreated : WaitingIntent()
}