package com.viktoriagavrosh.flightsearch.model

import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.model.database.FavoriteRoute

data class Route(
    val departureAirport: Airport,
    val arrivalAirport: Airport,
    var isFavorite: Boolean = false
) {

    fun toFavoriteRoute(): FavoriteRoute = FavoriteRoute(
        departureCode = departureAirport.code,
        arrivalCode = arrivalAirport.code
    )

    fun changeIsFavorite() {
        isFavorite = !isFavorite
    }
}
