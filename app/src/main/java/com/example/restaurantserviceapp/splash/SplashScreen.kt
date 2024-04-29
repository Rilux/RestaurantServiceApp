package com.example.restaurantserviceapp.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.restaurantserviceapp.destinations.LoginScreenDestination
import com.example.restaurantserviceapp.splash.model.SplashIntent
import com.example.restaurantserviceapp.splash.model.SplashSideEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
@Destination
@RootNavGraph(start = true)
fun SplashScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<SplashViewModel>()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.ShowLogin -> navigator.navigate(LoginScreenDestination)
            is SplashSideEffect.ShowAdminContent -> {}
            is SplashSideEffect.ShowServerContent -> {}
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xff0d99ff)
            )
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.handleIntent(SplashIntent.OnViewCreated)
    }


}