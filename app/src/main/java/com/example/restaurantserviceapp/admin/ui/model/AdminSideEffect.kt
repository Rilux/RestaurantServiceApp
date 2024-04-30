package com.example.restaurantserviceapp.admin.ui.model

sealed class AdminSideEffect {

    data object ShowDateChooseDialog : AdminSideEffect()
}