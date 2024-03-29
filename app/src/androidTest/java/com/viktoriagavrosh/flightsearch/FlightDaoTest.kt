package com.viktoriagavrosh.flightsearch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.data.FlightDao
import com.viktoriagavrosh.flightsearch.data.FlightDatabase
import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.FavoriteRoute
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class FlightDaoTest {
    private lateinit var flightDao: FlightDao
    private lateinit var flightDatabase: FlightDatabase
    private val airport1 = Airport(1, "A Airport", "AAA", 111)
    private val airport2 = Airport(2, "B Airport", "BBB", 222)
    private val favoriteRoute1 = FavoriteRoute(1, "AAA", "BBB")
    private val favoriteRoute2 = FavoriteRoute(2, "CCC", "DDD")
    private val route1 = Route(airport1, airport2)


    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        flightDatabase = Room.inMemoryDatabaseBuilder(context, FlightDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        flightDao = flightDatabase.flightDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        flightDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllRoutes_returnListRoutesFromDb() = runBlocking {
        flightDao.insertRoute(favoriteRoute1)
        flightDao.insertRoute(favoriteRoute2)
        val expectedList = listOf(favoriteRoute1, favoriteRoute2)
        val routes = flightDao.getAllRoutes().first()
        assertEquals(expectedList, routes)
    }

    @Test
    fun daoInsertRoute_InsertSuccess() = runBlocking {
        flightDao.insertRoute(route1.toFavoriteRoute())
        assertEquals(favoriteRoute1, flightDao.getAllRoutes().first().first())
    }

}
