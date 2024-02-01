package com.viktoriagavrosh.flightsearch.model

import com.viktoriagavrosh.flightsearch.model.database.Airport

data class Route(
    val departureAirport: Airport,
    val arrivalAirport: Airport,
    val isFavorite: Boolean = false
)