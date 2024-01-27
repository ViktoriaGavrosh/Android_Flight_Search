package com.viktoriagavrosh.flightsearch

import android.app.Application
import com.viktoriagavrosh.flightsearch.data.FlightDatabase

class FlightSearchApplication : Application() {
    val database: FlightDatabase by lazy {
        FlightDatabase.getDatabase(this)
    }
}