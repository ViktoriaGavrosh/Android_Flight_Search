package com.viktoriagavrosh.flightsearch.temporary

import com.viktoriagavrosh.flightsearch.data.Airport


object Datasource {
    val listAirports = listOf<Airport>(
        Airport(
            id = 1,
            code = "OAN",
            name = "El Arrayan Airport",
            passengers = 4
        ),
        Airport(
            id = 2,
            code = "OBA",
            name = "Oban Airport",
            passengers = 5
        ),
        Airport(
            id = 3,
            code = "AAF",
            name = "Apalachicola Regional Airport",
            passengers = 6
        )
    )

    fun getAirport(code: String): Airport {
        return listAirports.find { it.code == code } ?: listAirports[0]
    }
}