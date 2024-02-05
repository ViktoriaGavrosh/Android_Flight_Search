package com.viktoriagavrosh.flightsearch.domain

import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.model.database.FullRoute


interface Converter {
    fun convertAirportsToListRoutes(
        departureAirport: Airport,
        airports: List<Airport>,
        listFavoriteRoutes: List<Route>
    ): List<Route>

    fun convertListFullRoutesToListRoutes(
        favoriteRoutes: List<FullRoute>
    ): List<Route>
}

object RoutesConverter : Converter {
    override fun convertAirportsToListRoutes(
        departureAirport: Airport,
        airports: List<Airport>,
        listFavoriteRoutes: List<Route>
    ): List<Route> {
        val newListAirports = deleteAirport(departureAirport, airports)
        val listRoutes = newListAirports.map {
            val route = Route(departureAirport, it)
            val list = listFavoriteRoutes.filter { favoriteRoute ->
                favoriteRoute.departureAirport.code == route.departureAirport.code
                        && favoriteRoute.arrivalAirport.code == route.arrivalAirport.code
            }
            route.isFavorite = list.isNotEmpty()
            route
        }
        return listRoutes
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
            ),
            isFavorite = true
        )
    }


    private fun deleteAirport(airport: Airport, airports: List<Airport>) = airports.filter {
        it != airport
    }
}