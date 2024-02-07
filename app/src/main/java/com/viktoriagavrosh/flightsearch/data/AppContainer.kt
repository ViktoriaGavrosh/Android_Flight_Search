package com.viktoriagavrosh.flightsearch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val SEARCH_TEXT_PREFERENCES_NAME = "search_text_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SEARCH_TEXT_PREFERENCES_NAME
)

interface AppContainer {
    val flightRepository :FlightRepository
    val userPreferencesRepository: UserPreferencesRepository
}

class AppDataContainer(private val context: Context) : AppContainer{

    override val flightRepository: FlightRepository by lazy {
        DbFlightRepository(FlightDatabase.getDatabase(context).flightDao())
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(dataStore = context.dataStore)
    }
}