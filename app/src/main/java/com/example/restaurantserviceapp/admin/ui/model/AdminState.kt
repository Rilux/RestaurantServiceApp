package com.example.restaurantserviceapp.admin.ui.model

import android.os.Parcelable
import com.example.restaurantserviceapp.ui.base.InstantParceler
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

@Parcelize
data class AdminState(
    val isLoading: Boolean,
    val ordersNumberForChart: Map<@WriteWith<InstantParceler> Instant, Int>,
    val income: Long,
    val numOfOrders: Long,
    val tips: Long,
    val ordersForList: List<Order>,
) : Parcelable {

    fun setNewLoading(newLoading: Boolean) = this.copy(isLoading = newLoading)


    fun setNewOrdersForList(newList: List<Order>) = this.copy(ordersForList = newList)
    fun setNewDataForChart(data: Map<Instant, Int>) = this.copy(ordersNumberForChart = data)

    companion object {
        fun initial(): AdminState = AdminState(
            isLoading = true,
            ordersNumberForChart = emptyMap(),
            income = 0,
            numOfOrders = 0,
            tips = 0,
            ordersForList = emptyList()
        )
    }
}