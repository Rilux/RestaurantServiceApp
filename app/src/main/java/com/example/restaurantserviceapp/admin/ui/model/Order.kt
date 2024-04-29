package com.example.restaurantserviceapp.admin.ui.model

import android.os.Parcelable
import com.example.restaurantserviceapp.ui.base.InstantParceler
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

@Parcelize
data class Order(
    val isActive: Boolean = false,
    val isPaid: Boolean = false,
    val items: List<String> = emptyList(), // Assuming `items` is a list of item IDs or names.
    val table: Long = 0,
    val time: @WriteWith<InstantParceler> Instant = Instant.DISTANT_PAST, // Using kotlinx.datetime.Instant for the timestamp.
    val tips: Long = 0,
    val value: Long = 0,
) : Parcelable