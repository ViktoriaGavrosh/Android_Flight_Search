package com.viktoriagavrosh.flightsearch

import androidx.lifecycle.ViewModel
import com.viktoriagavrosh.flightsearch.temporary.Datasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlightViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    fun updateInputText(text: String) {
        _uiState.update {
            it.copy(
                inputText = text
            )
        }
    }

    fun updateAirport(code: String) {
        val newAirport = Datasource.getAirport(code)
        _uiState.update {
            it.copy(
                airport = newAirport
            )
        }
    }

}

data class FlightUiState(
    val inputText: String = "",
    val airport: Airport = Datasource.listAirports[0]
)

data class Airport(
    val code: String,
    val name: String
)