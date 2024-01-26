package com.viktoriagavrosh.flightsearch.temporary

import com.viktoriagavrosh.flightsearch.Airport

object Datasource {
    val listAirports = listOf<Airport>(
        Airport(
            code = "OAN",
            name = "El Arrayan Airport"
        ),
        Airport(
            code = "OBA",
            name = "Oban Airport"
        ),
        Airport(
            code = "AAF",
            name = "Apalachicola Regional Airport"
        )
    )

    fun getAirport(code: String): Airport {
        return listAirports.find { it.code == code } ?: listAirports[0]
    }
}