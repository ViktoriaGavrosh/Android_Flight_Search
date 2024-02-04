package com.viktoriagavrosh.flightsearch.data

import com.viktoriagavrosh.flightsearch.domain.RoutesConverter
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.model.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface FlightRepository {
    fun getAirportsStreamByCondition(code: String): Flow<List<Airport>>

    suspend fun getListRoutes(airport: Airport): List<Route>

   // fun getFavoriteRoutesStream(): Flow<List<FavoriteRoute>>     TODO

    suspend fun insertFavoriteRoute(route: Route)

    suspend fun deleteFavoriteRoute(route: Route)

    suspend fun getFavoriteRoutes(): List<Route>
}

class DbFlightRepository(private val flightDao: FlightDao) : FlightRepository {
    override fun getAirportsStreamByCondition(code: String) = flightDao.getAirportsByCondition(code)

    override suspend fun getListRoutes(airport: Airport): List<Route> {
        val allAirports = flightDao.getAllAirports().first()
        return RoutesConverter.convertAirportsToListRoutes(airport, allAirports)
    }

    override suspend fun getFavoriteRoutes(): List<Route> {
        val listFavoriteRoutes = flightDao.getAllFavoriteRoutes().first()
        return RoutesConverter.convertListFullRoutesToListRoutes(listFavoriteRoutes)
    }

    //override fun getFavoriteRoutesStream() = flightDao.getAllRoutes()      TODO

    override suspend fun insertFavoriteRoute(route: Route) {
        flightDao.insertRoute(route.toFavoriteRoute())
    }

    override suspend fun deleteFavoriteRoute(route: Route) {
        flightDao.deleteRoute(route.departureAirport.code, route.arrivalAirport.code)
    }


}