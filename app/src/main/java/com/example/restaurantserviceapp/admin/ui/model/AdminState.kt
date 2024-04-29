package com.example.restaurantserviceapp.admin.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminState(
    val isLoading: Boolean,
) : Parcelable {

    fun setNewLoading(newLoading: Boolean) = this.copy(isLoading = newLoading)


    companion object {
        fun initial(): AdminState = AdminState(true)
    }
}