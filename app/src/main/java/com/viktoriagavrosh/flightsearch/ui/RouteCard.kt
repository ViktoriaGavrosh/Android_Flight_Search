package com.viktoriagavrosh.flightsearch.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun RouteCard(
    modifier: Modifier = Modifier,
    route: Route,
    onStarClick: (Route) -> Unit
) {
    var starState by remember{
        mutableStateOf(route.isFavorite)
    }

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
            Box(
                modifier = Modifier
                    .weight(1F),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (starState) {
                        painterResource(id = R.drawable.ic_dark_star)
                    } else {
                        painterResource(id = R.drawable.ic_white_star)
                    },
                    contentDescription = stringResource(R.string.favorite_route),
                    modifier = Modifier.clickable {
                        onStarClick(route)
                        starState = !starState
                    }
                )
            }
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
                .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            onStarClick = {}
        )
    }
}