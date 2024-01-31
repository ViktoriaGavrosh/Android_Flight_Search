package com.viktoriagavrosh.flightsearch.model

data class Route(
    val departureAirport: Airport,
    val arrivalAirport: Airport,
    val isFavorite: Boolean = false
)