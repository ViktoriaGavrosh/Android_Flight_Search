package com.viktoriagavrosh.flightsearch.domain

import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.model.database.FullRoute


interface Converter {
    fun convertAirportsToListRoutes(
        departureAirport: Airport,
        airports: List<Airport>
    ): List<Route>

    fun convertListFullRoutesToListRoutes(
        favoriteRoutes: List<FullRoute>
    ): List<Route>
}
object RoutesConverter : Converter {
    override fun convertAirportsToListRoutes(
        departureAirport: Airport,
        airports: List<Airport>
    ): List<Route> {
        val newListAirports = deleteAirport(departureAirport, airports)
        return newListAirports.map { Route(departureAirport, it) }
    }

    override fun convertListFullRoutesToListRoutes(
        favoriteRoutes: List<FullRoute>
    ): List<Route> = favoriteRoutes.map {
        Route(
            Airport(
                name = it.departureName,
                code = it.departureCode
            ),
            Airport(
                name = it.arrivalName,
                code = it.arrivalCode
            )
        )
    }


    private fun deleteAirport(airport: Airport, airports: List<Airport>) = airports.filter {
        it != airport
    }
}