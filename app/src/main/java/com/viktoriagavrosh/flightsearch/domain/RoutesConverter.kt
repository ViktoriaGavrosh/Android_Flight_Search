package com.viktoriagavrosh.flightsearch.domain

import com.viktoriagavrosh.flightsearch.model.Airport
import com.viktoriagavrosh.flightsearch.model.Route


interface Converter {
    fun convertToListRoutes(
        departureAirport: Airport,
        airports: List<Airport>
    ): List<Route>
}
class RoutesConverter : Converter {
    override fun convertToListRoutes(
        departureAirport: Airport,
        airports: List<Airport>
    ): List<Route> {
        val newListAirports = deleteAirport(departureAirport, airports)
        return newListAirports.map { Route(departureAirport, it) }
    }

    private fun deleteAirport(airport: Airport, airports: List<Airport>) = airports.filter {
        it != airport
    }
}