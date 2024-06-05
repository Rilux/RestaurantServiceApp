package com.example.restaurantserviceapp.waiter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.restaurantserviceapp.NavGraphs
import com.example.restaurantserviceapp.destinations.LoginScreenDestination
import com.example.restaurantserviceapp.ui.components.LoadingFullScreen
import com.example.restaurantserviceapp.waiter.component.WaiterComposable
import com.example.restaurantserviceapp.waiter.model.WaiterIntent
import com.example.restaurantserviceapp.waiter.model.WaiterSideEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
@Destination
fun WaiterScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<WaiterViewModel>()
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when(it) {
            WaiterSideEffect.OnNavigateToLogin -> {
                navigator.navigate(LoginScreenDestination) {
                    popUpTo(NavGraphs.root)
                }
            }
        }
    }

    if (state.isLoading && state.numOfOrders == null) {
        LoadingFullScreen()
    } else {
        WaiterComposable(
            state = state,
            onExit = {
                viewModel.handleIntent(WaiterIntent.OnExit)
            },
            onDateChosen = {
                viewModel.handleIntent(WaiterIntent.OnDateChosen(it))
            }
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.handleIntent(WaiterIntent.OnLoadData)
    }

}