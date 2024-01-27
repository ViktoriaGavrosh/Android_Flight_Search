package com.viktoriagavrosh.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viktoriagavrosh.flightsearch.FlightSearchApplication
import com.viktoriagavrosh.flightsearch.data.Airport
import com.viktoriagavrosh.flightsearch.data.FlightDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightViewModel(
    private val flightDao: FlightDao
) : ViewModel() {

    private var _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    /*init {
        initState(1)
    }

    private fun initState(id: Int) {
        viewModelScope.launch {
           val airport = flightDao
                .getAirportById(id)
                .first()
            _uiState.update {
                it.copy(
                    airport = airport
                )
            }
        }
    }*/

    fun updateInputText(text: String) {
        _uiState.update {
            it.copy(
                inputText = text,
                isSearch = true
            )
        }
        updateListAirports(text)
    }

    private fun updateListAirports(text: String) {
        viewModelScope.launch {
            val newAirport = flightDao.getAirport(text).first()
            _uiState.update {
                it.copy(
                   listAirports = newAirport
                )
            }
        }
    }

    fun updateAirport(code: String) {
        viewModelScope.launch {
            val newAirport = flightDao.getAirport(code).first()
            _uiState.update {
                it.copy(
                    airport = newAirport.first(),
                    isSearch = false
                )
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightViewModel(application.database.flightDao())
            }
        }
    }

}

data class FlightUiState(
    val inputText: String = "",
    val airport: Airport = Airport(1, "", "", 1),
    val listAirports: List<Airport> = emptyList(),
    val isSearch: Boolean = true
)

