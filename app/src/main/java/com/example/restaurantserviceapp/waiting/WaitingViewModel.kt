package com.example.restaurantserviceapp.waiting

import androidx.lifecycle.SavedStateHandle
import com.example.restaurantserviceapp.ui.base.BaseMviViewModel
import com.example.restaurantserviceapp.ui.base.EmptyState
import com.example.restaurantserviceapp.waiting.model.WaitingIntent
import com.example.restaurantserviceapp.waiting.model.WaitingSideEffect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WaitingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : BaseMviViewModel<EmptyState, WaitingSideEffect, WaitingIntent>() {

    override val container =
        container<EmptyState, WaitingSideEffect>(
            initialState = EmptyState(),
            savedStateHandle = savedStateHandle
        )

    override fun handleIntent(intent: WaitingIntent) {
        when (intent) {
            WaitingIntent.OnViewCreated -> {
                onLoadData()
            }
        }
    }

    private fun onLoadData() {
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            firestore.collection("users")
                .whereEqualTo("email", currentUser.email)
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        Timber.w("Listen failed.", e)
                        return@addSnapshotListener
                    }

                    for (doc in value!!) {
                        doc.getString("role")?.let {
                            if (it == "Admin") {
                                postSideEffect(WaitingSideEffect.OnNavigateToAdmin)
                            } else if (it == "Waiter") {
                                postSideEffect(WaitingSideEffect.OnNavigateToWaiterPage)
                            } else {

                            }
                        }
                    }
                }
        }

    }


}