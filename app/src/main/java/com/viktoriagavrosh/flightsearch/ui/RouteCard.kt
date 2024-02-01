package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun RouteCard(
    modifier: Modifier = Modifier,
    route: Route
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            RouteDestination(
                airport = route.departureAirport,
                titleDestination = stringResource(id = R.string.depart)
            )
            RouteDestination(
                airport = route.arrivalAirport,
                titleDestination = stringResource(id = R.string.arrive),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_medium))
            )
        }

    }
}

@Composable
private fun RouteDestination(
    modifier: Modifier = Modifier,
    airport: Airport,
    titleDestination: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = titleDestination,
            style = MaterialTheme.typography.labelSmall
        )
        AirportRow(
            airport = airport,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Preview
@Composable
fun RouteCardPreview() {
    val mockAirport = Airport(1, "Airport", "AAA", 1)
    val mockRoute = Route(mockAirport, mockAirport)
    FlightSearchTheme {
        RouteCard(
            route = mockRoute,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.padding_medium))
        )
    }
}