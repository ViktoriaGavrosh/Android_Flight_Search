package com.viktoriagavrosh.flightsearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.viktoriagavrosh.flightsearch.model.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Insert
    suspend fun insertAirport(airport: Airport)

    @Query("SELECT * FROM airport WHERE iata_code LIKE '%' || :code || '%' " +
            "OR name LIKE '%' || :code || '%'")
    fun getAirport(code: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE id = :id")
    fun getAirportById(id: Int): Flow<Airport>

    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

}