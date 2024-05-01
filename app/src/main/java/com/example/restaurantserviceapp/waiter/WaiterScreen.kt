package com.example.restaurantserviceapp.waiter

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.restaurantserviceapp.waiter.model.WaiterIntent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun WaiterScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<WaiterViewModel>()


    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.handleIntent(WaiterIntent.OnLoadData)
    }

}