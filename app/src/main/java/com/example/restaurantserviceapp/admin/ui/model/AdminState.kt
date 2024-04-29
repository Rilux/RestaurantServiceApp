package com.example.restaurantserviceapp.admin.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminState(
    val isLoading: Boolean,
    val ordersForTheChart: List<Order>,
    val income: Long,
    val numOfOrders: Long,
    val tips: Long,
    val ordersForList: List<Order>,
) : Parcelable {

    fun setNewLoading(newLoading: Boolean) = this.copy(isLoading = newLoading)


    companion object {
        fun initial(): AdminState = AdminState(
            isLoading = true,
            ordersForTheChart = emptyList(),
            income = 0,
            numOfOrders = 0,
            tips = 0,
            ordersForList = emptyList()
        )
    }
}