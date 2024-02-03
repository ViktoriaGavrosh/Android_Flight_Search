package com.viktoriagavrosh.flightsearch.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteRoute(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "departure_code") val departureCode: String,
    @ColumnInfo(name = "destination_code") val arrivalCode: String
)