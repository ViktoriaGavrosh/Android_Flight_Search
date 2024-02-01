package com.viktoriagavrosh.flightsearch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.data.FlightDao
import com.viktoriagavrosh.flightsearch.data.FlightDatabase
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
    private val airport1 = Airport(1, "AAA", "A Airport", 111)
    private val airport2 = Airport(2, "BBB", "B Airport", 222)

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
    fun daoGetAirportById_returnAirportFromDb() = runBlocking {
        flightDao.insertAirport(airport1)
        val airport = flightDao.getAirportById(1)
        assertEquals(airport.first(), airport1)
    }
}