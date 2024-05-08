package com.example.restaurantserviceapp.admin.ui.model

import kotlinx.datetime.Instant

sealed class AdminIntent {

    data object OnLoadData : AdminIntent()



    data class OnDateChosen(val date: Instant) : AdminIntent()

}
