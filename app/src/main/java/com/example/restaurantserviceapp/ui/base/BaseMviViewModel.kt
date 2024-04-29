package com.example.restaurantserviceapp.ui.base

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

abstract class BaseMviViewModel<STATE : Parcelable, SIDE_EFFECT : Any, INTENT : Any> :
    ContainerHost<STATE, SIDE_EFFECT>,
    IntentHandler<INTENT>,
    ViewModel() {


    protected fun updateState(newState: (STATE) -> STATE) = intent {
        reduce {
            newState(state)
        }
    }

    protected fun postSideEffect(sideEffect: SIDE_EFFECT) = intent {
        this.postSideEffect(sideEffect)
    }

    override fun handleIntent(intent: INTENT) {}

}