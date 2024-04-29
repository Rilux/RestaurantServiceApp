package com.example.restaurantserviceapp.login.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.restaurantserviceapp.login.ui.model.LoginIntent
import com.example.restaurantserviceapp.login.ui.model.LoginSideEffect
import com.example.restaurantserviceapp.ui.base.BaseMviViewModel
import com.example.restaurantserviceapp.ui.base.EmptyState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firebaseAuth: FirebaseAuth,

    ) : BaseMviViewModel<EmptyState, LoginSideEffect, LoginIntent>() {

    override val container =
        container<EmptyState, LoginSideEffect>(
            initialState = EmptyState(),
            savedStateHandle = savedStateHandle
        )


    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.OnCredentialsEntered -> logInUser(intent.email, intent.password)
        }
    }


    private fun logInUser(email: String, password: String) {
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithEmail:success")
                        val user = firebaseAuth.currentUser

                        postSideEffect(LoginSideEffect.NavigateToAdminPage)


                    } else {

                        // If sign in fails, display a message to the user.
                        Timber.w("signInWithEmail:failure", task.exception)

                        postSideEffect(LoginSideEffect.ShowErrorMessage)

                    }
                }

        }

    }

}
