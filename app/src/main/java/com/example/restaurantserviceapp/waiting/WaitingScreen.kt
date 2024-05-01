package com.example.restaurantserviceapp.waiting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.restaurantserviceapp.destinations.AdminScreenDestination
import com.example.restaurantserviceapp.waiting.model.WaitingIntent
import com.example.restaurantserviceapp.waiting.model.WaitingSideEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
@Destination
fun WaitingScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<WaitingViewModel>()

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.handleIntent(WaitingIntent.OnViewCreated)
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            WaitingSideEffect.OnNavigateToAdmin -> navigator.navigate(AdminScreenDestination)
            WaitingSideEffect.OnNavigateToWaiterPage -> {}
        }
    }


    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}