package com.example.restaurantserviceapp.admin.ui.model

sealed class AdminIntent {

    data object OnLoadData : AdminIntent()

    data object OnTodayChosen : AdminIntent()

    data object OnYesterdayChosen : AdminIntent()

}
