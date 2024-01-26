package com.viktoriagavrosh.flightsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FlightSearchApp() {

    val flightViewModel: FlightViewModel = viewModel()
    val uiState = flightViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.value.inputText,
            onValueChange = { flightViewModel.updateInputText(it) }
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Row(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = uiState.value.airport.code,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = uiState.value.airport.name,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Preview
@Composable
fun FlightSearchAppPreview() {
    FlightSearchTheme {
        FlightSearchApp()
    }
}