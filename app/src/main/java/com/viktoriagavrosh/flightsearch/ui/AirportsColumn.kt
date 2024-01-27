package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.data.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun AirportsColumn(
    modifier: Modifier = Modifier,
    airports: List<Airport>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = airports
        ) {
            AirportRow(
                airport = it
            )
        }
    }
}

@Preview
@Composable
fun AirportsColumnPreview() {
    val mockAirports = List(3) {
        Airport(it, "Airport", "$it", it)
    }
    FlightSearchTheme {
        AirportsColumn(
            airports = mockAirports
        )
    }
}