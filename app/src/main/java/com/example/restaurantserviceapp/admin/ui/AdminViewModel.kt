package com.example.restaurantserviceapp.admin.ui

import androidx.lifecycle.SavedStateHandle
import com.example.restaurantserviceapp.admin.ui.model.AdminIntent
import com.example.restaurantserviceapp.admin.ui.model.AdminState
import com.example.restaurantserviceapp.admin.ui.model.Order
import com.example.restaurantserviceapp.ui.base.BaseMviViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firestore: FirebaseFirestore,
) : BaseMviViewModel<AdminState, Nothing, AdminIntent>() {

    override val container =
        container<AdminState, Nothing>(
            initialState = AdminState.initial(),
            savedStateHandle = savedStateHandle
        )


    override fun handleIntent(intent: AdminIntent) {
        when (intent) {
            AdminIntent.OnLoadData -> loadData()
        }
    }


    private fun loadData() {
        val now = Clock.System.now()
        val tz = TimeZone.currentSystemDefault()

        // Get the start of today (midnight at the beginning of today)
        val startOfDayLocalDateTime = now.toLocalDateTime(tz).date.atStartOfDayIn(tz).toLocalDateTime(tz).toJavaLocalDateTime()
        val startOfDayTimestamp = Timestamp(startOfDayLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond, 0)

        // Get the end of today (one second before midnight at the end of today)
        val endOfDayLocalDateTime = startOfDayLocalDateTime.plusDays(1).minusNanos(1)
        val endOfDayTimestamp = Timestamp(endOfDayLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond, endOfDayLocalDateTime.nano)

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
                }

                updateState { it.setNewOrdersForList(orders) }

                orders.forEach { order ->
                    Timber.d("$order")
                }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents: ", exception)
            }
    }



}