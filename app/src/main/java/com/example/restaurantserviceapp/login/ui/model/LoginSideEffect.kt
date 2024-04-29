package com.example.restaurantserviceapp.login.ui.model

sealed class LoginSideEffect {

    data object NavigateToAdminPage : LoginSideEffect()

    data object NavigateToWaitingPage : LoginSideEffect()

    data object NavigateToWaiterPage : LoginSideEffect()

    data object ShowErrorMessage : LoginSideEffect()
}