package com.example.restaurantserviceapp.waiter.model


sealed class WaiterSideEffect {
    data object ShowDateChooseDialog : WaiterSideEffect()

}