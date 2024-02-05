package com.viktoriagavrosh.flightsearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.model.database.FavoriteRoute
import com.viktoriagavrosh.flightsearch.model.database.FullRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Insert
    suspend fun insertAirport(airport: Airport)

    @Query("SELECT * FROM airport WHERE iata_code LIKE '%' || :code || '%' " +
            "OR name LIKE '%' || :code || '%'")
    fun getAirportsByCondition(code: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE id = :id")
    fun getAirportById(id: Int): Flow<Airport>


    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

    @Insert
    suspend fun insertRoute(route: FavoriteRoute)

    @Query("DELETE FROM favorite WHERE departure_code = :departureCode AND destination_code = :arrivalCode")
    suspend fun deleteRoute(departureCode: String, arrivalCode: String)

    @Query("SELECT * FROM favorite")
    fun getAllRoutes(): Flow<List<FavoriteRoute>>

    @Query("SELECT f.id, departure_code, a.name AS 'departure_name', destination_code, b.name AS " +
            "'destination_name' FROM favorite f INNER JOIN airport a ON f.departure_code = " +
            "a.iata_code INNER JOIN airport b ON f.destination_code = b.iata_code")
    fun getAllFavoriteRoutes(): Flow<List<FullRoute>>

    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND " +
            "destination_code = :destinationCode")
    fun getRoute(departureCode: String, destinationCode: String): Flow<List<FavoriteRoute>>
}