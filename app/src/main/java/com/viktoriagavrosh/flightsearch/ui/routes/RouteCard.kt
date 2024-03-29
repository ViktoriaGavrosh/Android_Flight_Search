package com.viktoriagavrosh.flightsearch.ui.routes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.ui.airports.AirportRow
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun RouteCard(
    modifier: Modifier = Modifier,
    route: Route,
    onStarClick: (Route) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .weight(5F)
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
            RouteStatusIndicator(
                modifier = Modifier
                    .weight(1F),
                route = route,
                onStarClick = onStarClick
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

@Composable
private fun RouteStatusIndicator(
    modifier: Modifier = Modifier,
    route: Route,
    onStarClick: (Route) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (route.isFavorite) {
                painterResource(id = R.drawable.ic_dark_star)
            } else {
                painterResource(id = R.drawable.ic_white_star)
            },
            contentDescription = stringResource(R.string.favorite_route),
            modifier = Modifier.clickable {
                onStarClick(route)
            }
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
                .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            onStarClick = {}
        )
    }
}