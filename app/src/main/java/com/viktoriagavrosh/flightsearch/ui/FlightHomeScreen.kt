package com.viktoriagavrosh.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.viktoriagavrosh.flightsearch.R
import com.viktoriagavrosh.flightsearch.data.Airport
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FlightHomeScreen(
    modifier: Modifier = Modifier,
    uiState: FlightUiState,
    onTextChange: (String) -> Unit,
    onUpdateAirport: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.inputText,
            onValueChange = onTextChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions {
                onUpdateAirport(uiState.inputText)
            }
        )
        if (uiState.isSearch) {
            AirportsColumn(
                airports = List(3) {
                    Airport(it, "Airport", "$it", it)     //TODO
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium))
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
    val mockAirport =  Airport(1, "Airport", "AAA", 1)
    FlightSearchTheme {
        FlightHomeScreen(
            uiState = FlightUiState("text", mockAirport, false),
            onTextChange = {},
            onUpdateAirport = {}
        )
    }
}