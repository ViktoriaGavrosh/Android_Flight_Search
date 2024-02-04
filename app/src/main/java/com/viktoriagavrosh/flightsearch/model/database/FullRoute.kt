package com.viktoriagavrosh.flightsearch.model.database

import androidx.room.ColumnInfo

data class FullRoute(
    val id: Int,
    @ColumnInfo(name = "departure_code") val departureCode: String,
    @ColumnInfo(name = "departure_name") val departureName: String,
    @ColumnInfo(name = "destination_code") val arrivalCode: String,
    @ColumnInfo(name = "destination_name") val arrivalName: String
)