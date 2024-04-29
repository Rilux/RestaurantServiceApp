package com.example.restaurantserviceapp.admin.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.restaurantserviceapp.admin.ui.model.AdminIntent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun AdminScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<AdminViewModel>()

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.handleIntent(AdminIntent.OnLoadData)
    }




}