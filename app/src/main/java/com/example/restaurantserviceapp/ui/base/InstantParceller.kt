package com.example.restaurantserviceapp.ui.base

import android.os.Parcel
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parceler

object InstantParceler : Parceler<Instant> {
    override fun create(parcel: Parcel): Instant {
        // Assuming the Instant is stored as a Long representing epoch milliseconds
        return Instant.fromEpochMilliseconds(parcel.readLong())
    }

    override fun Instant.write(parcel: Parcel, flags: Int) {
        // Write the Instant to the parcel as a Long
        parcel.writeLong(this.toEpochMilliseconds())
    }
}