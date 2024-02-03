package com.viktoriagavrosh.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.model.database.FavoriteRoute

@Database(
    entities = [Airport::class, FavoriteRoute::class],
    version = 2
)
abstract class FlightDatabase : RoomDatabase() {
    abstract fun flightDao(): FlightDao

    companion object {
        private var Instance: FlightDatabase? = null

        // there is an application crash without migration_1_2
        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
            }
        }

        fun getDatabase(context: Context): FlightDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightDatabase::class.java, "app_database")
                    .createFromAsset("database/flight_search.db")
                    .addMigrations(migration_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}