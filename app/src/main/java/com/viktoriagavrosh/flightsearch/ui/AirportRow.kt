package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.model.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun AirportRow(
    modifier: Modifier = Modifier,
    airport: Airport
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = airport.code,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = airport.name,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun AirportRowPreview() {
    val mockAirport = Airport(1, "Airport", "AAA", 1)
    FlightSearchTheme {
        AirportRow(airport = mockAirport)
    }
}