package com.example.restaurantserviceapp.waiter.model

import kotlinx.datetime.Instant

sealed class WaiterIntent {

    data object OnLoadData : WaiterIntent()


    data class OnDateChosen(val date: Instant) : WaiterIntent()

}