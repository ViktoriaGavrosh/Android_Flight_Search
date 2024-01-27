package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.data.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun RoutesColumn(
    modifier: Modifier = Modifier,
    airport: Airport
) {
    LazyColumn(
        modifier = modifier
    ) {
        item(
            content = {
               RouteCard(
                   airport = airport,
                   modifier = Modifier
                       .fillMaxWidth()
               )
            }
        )
    }
}

@Preview
@Composable
fun RouteColumnPreview() {
    val mockAirport = Airport(1, "Airport", "AAA", 1)
    FlightSearchTheme {
        RoutesColumn(airport = mockAirport)
    }
}