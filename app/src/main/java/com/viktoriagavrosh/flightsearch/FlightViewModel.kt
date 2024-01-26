package com.viktoriagavrosh.flightsearch

import androidx.lifecycle.ViewModel
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
}

data class FlightUiState(
    val inputText: String = "",
    val airport: Airport = Airport("AAF", "Apalachicola Regional Airport")
)

data class Airport(
    val code: String,
    val name: String
)