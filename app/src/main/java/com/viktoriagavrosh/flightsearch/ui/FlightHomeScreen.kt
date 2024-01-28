package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.data.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FlightHomeScreen(
    modifier: Modifier = Modifier,
    uiState: FlightUiState,
    onTextChange: (String) -> Unit,
    onAirportClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.inputText,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mic),
                        contentDescription = stringResource(R.string.microphone)
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                disabledIndicatorColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        if (uiState.isSearch) {
            AirportsColumn(
                airports = uiState.listAirports,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
                onAirportClick = onAirportClick
            )
        } else {
            RoutesColumn(
                airport = uiState.airport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview
@Composable
fun FlightHomeScreenPreview() {
    val mockAirport = Airport(1, "Airport", "AAA", 1)
    FlightSearchTheme {
        FlightHomeScreen(
            uiState = FlightUiState("text", mockAirport, emptyList()),
            onTextChange = {},
            onAirportClick = {}
        )
    }
}