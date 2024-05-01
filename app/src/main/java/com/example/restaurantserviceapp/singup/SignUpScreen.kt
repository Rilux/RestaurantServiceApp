package com.example.restaurantserviceapp.singup

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.restaurantserviceapp.destinations.WaitingScreenDestination
import com.example.restaurantserviceapp.singup.components.SignUpComposable
import com.example.restaurantserviceapp.singup.model.SignUpIntent
import com.example.restaurantserviceapp.singup.model.SignUpSideEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
@Destination
fun SignUpScreen(
    navigator: DestinationsNavigator,
) {

    val viewModel = hiltViewModel<SignUpViewModel>()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            SignUpSideEffect.OnShowError -> {
                Toast.makeText(context, "Unexpected error happened, try again", Toast.LENGTH_LONG).show()
            }

            SignUpSideEffect.OnNavigateToWaitScreen -> {
                navigator.navigate(WaitingScreenDestination)
            }
        }
    }

    SignUpComposable { fullName, email, password1, password2 ->
        viewModel.handleIntent(
            SignUpIntent.OnCreateUser(
                fullName, email, password1, password2
            )
        )
    }
}