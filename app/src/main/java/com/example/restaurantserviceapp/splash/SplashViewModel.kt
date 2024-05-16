package com.example.restaurantserviceapp.splash

import androidx.lifecycle.SavedStateHandle
import com.example.restaurantserviceapp.splash.model.SplashIntent
import com.example.restaurantserviceapp.splash.model.SplashSideEffect
import com.example.restaurantserviceapp.ui.base.BaseMviViewModel
import com.example.restaurantserviceapp.ui.base.EmptyState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firebaseAuth: FirebaseAuth,
) : BaseMviViewModel<EmptyState, SplashSideEffect, SplashIntent>() {


    override val container =
        container<EmptyState, SplashSideEffect>(
            initialState = EmptyState(),
            savedStateHandle = savedStateHandle
        )

    override fun handleIntent(intent: SplashIntent) {
        when(intent) {
            SplashIntent.OnViewCreated -> loadUser()
        }
    }


    private fun loadUser() {
        val user = firebaseAuth.currentUser
        if(user == null){
            postSideEffect(SplashSideEffect.ShowLogin)
        } else {
            postSideEffect(SplashSideEffect.ShowWaitingScreen)
        }
    }

}

