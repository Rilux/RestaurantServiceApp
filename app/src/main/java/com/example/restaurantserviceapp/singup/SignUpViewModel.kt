package com.example.restaurantserviceapp.singup

import androidx.lifecycle.SavedStateHandle
import com.example.restaurantserviceapp.singup.model.SignUpIntent
import com.example.restaurantserviceapp.singup.model.SignUpSideEffect
import com.example.restaurantserviceapp.ui.base.BaseMviViewModel
import com.example.restaurantserviceapp.ui.base.EmptyState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : BaseMviViewModel<EmptyState, SignUpSideEffect, SignUpIntent>() {

    override val container =
        container<EmptyState, SignUpSideEffect>(
            initialState = EmptyState(),
            savedStateHandle = savedStateHandle
        )

    override fun handleIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.OnCreateUser -> onRegisterUser(
                intent.userName,
                intent.email,
                intent.password,
                intent.repeatedPassword
            )
        }
    }

    private fun onRegisterUser(
        userName: String,
        email: String,
        password: String,
        repeatedPassword: String,
    ) {

        // Add a new document with a generated id.
        val data = hashMapOf(
            "fullName" to userName,
            "email" to email,
        )

        firestore.collection("users")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot written with ID: ${documentReference.id}")
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Timber.d("createUserWithEmail:success")
                            postSideEffect(SignUpSideEffect.OnNavigateToWaitScreen)
                        } else {
                            // If sign in fails, display a message to the user.
                            Timber.w("createUserWithEmail:failure", task.exception)
                            postSideEffect(SignUpSideEffect.OnShowError)
                        }
                    }
            }
            .addOnFailureListener { e ->
                postSideEffect(SignUpSideEffect.OnShowError)
                Timber.w("Error adding document", e)
            }
    }


}