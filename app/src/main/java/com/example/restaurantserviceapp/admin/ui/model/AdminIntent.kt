package com.example.restaurantserviceapp.admin.ui.model

import kotlinx.datetime.Instant

sealed class AdminIntent {

    data object OnLoadData : AdminIntent()

    data object OnTodayChosen : AdminIntent()

    data object OnYesterdayChosen : AdminIntent()

    data class OnDateChoosen(val date: Instant) : AdminIntent()

}
