package com.example.restaurantserviceapp.waiter

import androidx.lifecycle.SavedStateHandle
import com.example.restaurantserviceapp.admin.ui.groupOrdersByHour
import com.example.restaurantserviceapp.admin.ui.model.Order
import com.example.restaurantserviceapp.ui.base.BaseMviViewModel
import com.example.restaurantserviceapp.waiter.model.WaiterIntent
import com.example.restaurantserviceapp.waiter.model.WaiterSideEffect
import com.example.restaurantserviceapp.waiter.model.WaiterState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class WaiterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : BaseMviViewModel<WaiterState, WaiterSideEffect, WaiterIntent>() {

    private var userTables: MutableList<Long> = mutableListOf()

    override val container =
        container<WaiterState, WaiterSideEffect>(
            initialState = WaiterState.initial(),
            savedStateHandle = savedStateHandle
        )

    override fun handleIntent(intent: WaiterIntent) {
        when (intent) {
            WaiterIntent.OnLoadData -> getUser()
            WaiterIntent.OnTodayChosen -> {
                updateState {
                    it.setNewDate(Clock.System.now())
                }
                loadData()
            }

            WaiterIntent.OnYesterdayChosen -> {
                updateState {
                    it.setNewDate(
                        Clock.System.now()
                            .minus(1, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
                    )
                }

                loadData()
            }

            is WaiterIntent.OnDateChosen -> {
                updateState {
                    it.setNewDate(intent.date)
                }

                loadData()
            }
        }
    }

    private fun getUser() {
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
                        val numbersList = doc.get("tables") as? List<Number>
                        numbersList?.let { numbers ->
                            userTables.clear()

                            userTables.addAll(numbers.map { it.toLong() })  // Convert each Number to Long
                        }
                        loadData()
                    }
                }
        }
    }

    private fun loadData() = intent {

        val tz = TimeZone.currentSystemDefault()

        // Get the start of today (midnight at the beginning of today)
        val startOfDayLocalDateTime =
            state.currentDate.toLocalDateTime(tz).date.atStartOfDayIn(tz).toLocalDateTime(tz)
                .toJavaLocalDateTime()
        val startOfDayTimestamp = Timestamp(
            startOfDayLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond, 0
        )

        // Get the end of today (one second before midnight at the end of today)
        val endOfDayLocalDateTime = startOfDayLocalDateTime.plusDays(1).minusNanos(1)
        val endOfDayTimestamp = Timestamp(
            endOfDayLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond,
            endOfDayLocalDateTime.nano
        )

        // Reference to Firestore database


        firestore.collection("orders")
            .whereGreaterThanOrEqualTo("time", startOfDayTimestamp)
            .whereLessThanOrEqualTo("time", endOfDayTimestamp)
            .get()
            .addOnSuccessListener { documents ->
                val orders = documents.mapNotNull { document ->
                    val data = document.data
                    try {
                        val time = (data["time"] as Timestamp).toDate().toInstant().toEpochMilli()
                        Order(
                            id = document.id,
                            isActive = data["isActive"] as Boolean,
                            isPaid = data["isPaid"] as Boolean,
                            items = data["items"] as List<String>,
                            table = data["table"] as Long,
                            time = Instant.fromEpochMilliseconds(time),
                            tips = data["tips"] as Long,
                            value = data["value"] as Long
                        )
                    } catch (e: Exception) {
                        Timber.e(e, "Error deserializing document: ${document.id}")
                        null
                    }
                }.filter { userTables.contains(it.table) }

                updateState {
                    it.setNewDataForChart(groupOrdersByHour(orders))
                }

                updateState { it.setNewNumberOfOrders(orders.size.toLong()) }

                updateState {
                    it.setNewTipsAndIncome(
                        newIncome = orders.sumOf { it.value },
                        newTips = orders.sumOf { it.tips }
                    )
                }

                updateState {
                    it.setNewOrdersForList(orders.filter { it.isActive })
                }
                getOrdersProductName(orders)
                updateState { it.setNewLoading(false) }

            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents: ", exception)
            }
    }

    private fun getOrdersProductName(orders: List<Order>) {
        val newOrders = mutableListOf<Order>()
        firestore.collection("menu")
            .get()
            .addOnSuccessListener { documents ->
                orders.forEach { item ->

                    val titles =
                        documents.filter { item.items.contains(it.id) }.mapNotNull { document ->
                            val data = document.data
                            try {
                                data["Title"] as String
                            } catch (e: Exception) {
                                Timber.e(e, "Error deserializing document: ${document.id}")
                                null
                            }
                        }

                    newOrders.add(
                        item.copy(
                            items = titles
                        )
                    )
                }

                updateState { it.setNewOrdersForList(newOrders) }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents: ", exception)
            }
    }
}