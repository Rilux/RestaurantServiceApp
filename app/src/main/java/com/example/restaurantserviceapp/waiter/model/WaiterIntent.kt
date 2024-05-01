package com.example.restaurantserviceapp.waiter.model

import kotlinx.datetime.Instant

sealed class WaiterIntent {

    data object OnLoadData : WaiterIntent()

    data object OnTodayChosen : WaiterIntent()

    data object OnYesterdayChosen : WaiterIntent()

    data class OnDateChosen(val date: Instant) : WaiterIntent()

}