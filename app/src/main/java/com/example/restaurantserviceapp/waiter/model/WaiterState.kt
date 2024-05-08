package com.example.restaurantserviceapp.waiter.model

import android.os.Parcelable
import com.example.restaurantserviceapp.admin.ui.model.Order
import com.example.restaurantserviceapp.ui.base.InstantParceler
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

@Parcelize
data class WaiterState(
    val isLoading: Boolean,
    val currentDate: @WriteWith<InstantParceler> Instant,
    val ordersNumberForChart: Map<@WriteWith<InstantParceler> Instant, Int>,
    val numOfOrders: Long? ,
    val tips: Long,
    val ordersForList: List<Order>,
) : Parcelable {

    fun setNewLoading(newLoading: Boolean) = this.copy(isLoading = newLoading)

    fun setNewOrdersForList(newList: List<Order>) = this.copy(ordersForList = newList)

    fun setNewDate(newDate: Instant) = this.copy(currentDate = newDate)

    fun setNewDataForChart(data: Map<Instant, Int>) = this.copy(ordersNumberForChart = data)

    fun setNewTips(
        newTips: Long,
    ) = this.copy( tips = newTips)

    fun setNewNumberOfOrders(newNumber: Long) = this.copy(numOfOrders = newNumber)

    companion object {
        fun initial(): WaiterState = WaiterState(
            isLoading = true,
            currentDate = Clock.System.now(),
            ordersNumberForChart = emptyMap(),
            numOfOrders = null,
            tips = 0,
            ordersForList = emptyList()
        )
    }
}