package com.viktoriagavrosh.flightsearch

import android.app.Application
import com.viktoriagavrosh.flightsearch.data.AppContainer
import com.viktoriagavrosh.flightsearch.data.AppDataContainer

class FlightSearchApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}