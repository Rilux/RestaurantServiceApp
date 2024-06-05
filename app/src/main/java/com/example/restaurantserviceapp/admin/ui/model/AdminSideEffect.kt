package com.example.restaurantserviceapp.admin.ui.model


sealed class AdminSideEffect {
    data object OnNavigateToLogin : AdminSideEffect()

}