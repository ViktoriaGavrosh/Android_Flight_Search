package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.data.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun AirportsColumn(
    modifier: Modifier = Modifier,
    airports: List<Airport>,
    onAirportClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = airports
        ) {
            AirportRow(
                airport = it,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable {
                        onAirportClick(it.code)
                    }
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
            airports = mockAirports,
            onAirportClick = {}
        )
    }
}