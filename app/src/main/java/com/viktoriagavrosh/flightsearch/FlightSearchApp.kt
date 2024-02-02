package com.viktoriagavrosh.flightsearch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viktoriagavrosh.flightsearch.ui.FlightHomeScreen
import com.viktoriagavrosh.flightsearch.ui.FlightViewModel
import com.viktoriagavrosh.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FlightSearchApp() {

    val flightViewModel: FlightViewModel = viewModel(factory = FlightViewModel.factory)
    val uiState = flightViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { FlightTopAppBar() }
    ) {
        FlightHomeScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            uiState = uiState.value,
            onTextChange = { text ->flightViewModel.updateInputTextForSearch(text) },
            onAirportClick = { code -> flightViewModel.updateListRoutes(code) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlightTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.flight_search),
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview
@Composable
fun FlightSearchAppPreview() {
    FlightSearchTheme {
        FlightSearchApp()
    }
}