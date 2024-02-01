package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun RoutesColumn(
    modifier: Modifier = Modifier,
    airport: Airport,
    listRoutes: List<Route>
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.flight_from, airport.code),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_extra_medium))
        )
        LazyColumn {
            items(
                items = listRoutes
            ) { item ->
                RouteCard(
                    route = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Preview
@Composable
fun RouteColumnPreview() {
    val mockAirport = Airport(1, "Airport", "AAA", 1)
    val mockRoute = Route(mockAirport, mockAirport)
    FlightSearchTheme {
        RoutesColumn(
            airport = mockAirport,
            listRoutes = List(3) {
                mockRoute
            }
        )
    }
}