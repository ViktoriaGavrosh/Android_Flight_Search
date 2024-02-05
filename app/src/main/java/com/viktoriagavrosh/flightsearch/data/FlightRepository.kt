package com.viktoriagavrosh.flightsearch.data

import com.viktoriagavrosh.flightsearch.domain.Converter
import com.viktoriagavrosh.flightsearch.domain.RoutesConverter
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.model.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface FlightRepository {
    fun getAirportsStreamByCondition(code: String): Flow<List<Airport>>

    suspend fun getListRoutes(airport: Airport): List<Route>

    suspend fun insertFavoriteRoute(route: Route)

    suspend fun deleteFavoriteRoute(route: Route)

    suspend fun getFavoriteRoutes(): List<Route>

    suspend fun isContainFavoriteRoute(route: Route): Boolean
}

class DbFlightRepository(private val flightDao: FlightDao) : FlightRepository {

    private val converter: Converter = RoutesConverter

    override fun getAirportsStreamByCondition(code: String) = flightDao.getAirportsByCondition(code)
    override suspend fun isContainFavoriteRoute(route: Route): Boolean {
        val listRoutes = flightDao
            .getRoute(route.departureAirport.code, route.arrivalAirport.code).first()
        return listRoutes.isNotEmpty()
    }

    override suspend fun getListRoutes(airport: Airport): List<Route> {
        val allAirports = flightDao.getAllAirports().first()
        return converter.convertAirportsToListRoutes(airport, allAirports, getFavoriteRoutes())
    }

    override suspend fun getFavoriteRoutes(): List<Route> {
        val listFavoriteRoutes = flightDao.getAllFavoriteRoutes().first()
        return converter.convertListFullRoutesToListRoutes(listFavoriteRoutes)
    }

    override suspend fun insertFavoriteRoute(route: Route) {
        flightDao.insertRoute(route.toFavoriteRoute())
    }

    override suspend fun deleteFavoriteRoute(route: Route) {
        flightDao.deleteRoute(route.departureAirport.code, route.arrivalAirport.code)
    }


}