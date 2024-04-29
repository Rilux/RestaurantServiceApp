package com.example.restaurantserviceapp.ui.base

interface IntentHandler<INTENT : Any> {
    fun handleIntent(intent: INTENT)
}