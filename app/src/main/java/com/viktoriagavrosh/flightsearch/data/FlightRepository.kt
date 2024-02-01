package com.viktoriagavrosh.flightsearch.data

import com.viktoriagavrosh.flightsearch.domain.RoutesConverter
import com.viktoriagavrosh.flightsearch.model.Airport
import com.viktoriagavrosh.flightsearch.model.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface FlightRepository {
    fun getAirportsStreamByCondition(code: String): Flow<List<Airport>>

    suspend fun getListRoutes(airport: Airport): List<Route>
}

class DbFlightRepository(private val flightDao: FlightDao) : FlightRepository {
    override fun getAirportsStreamByCondition(code: String) = flightDao.getAirportsByCondition(code)

    override suspend fun getListRoutes(airport: Airport): List<Route> {
        val allAirports = flightDao.getAllAirports().first()
        return RoutesConverter.convertToListRoutes(airport, allAirports)
    }
}