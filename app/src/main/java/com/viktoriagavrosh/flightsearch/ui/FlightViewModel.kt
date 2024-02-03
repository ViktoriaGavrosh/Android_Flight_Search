package com.viktoriagavrosh.flightsearch.ui


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viktoriagavrosh.flightsearch.FlightSearchApplication
import com.viktoriagavrosh.flightsearch.data.FlightRepository
import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightViewModel(
    private val flightRepository: FlightRepository
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

    fun updateInputTextForSearch(text: String) {

        updateInputText(text)
        if (text.isEmpty()) {
            updateFalseSearchState()
        } else {
            updateListAirports(text)
        }
    }

    fun updateAirport(code: String) {
        viewModelScope.launch {
            val newAirport = flightRepository.getAirportsStreamByCondition(code).first()
            _uiState.update {
                it.copy(
                    selectedAirport = newAirport.first(),
                    isSearch = false
                )
            }
        }
    }

    fun updateListRoutes(code: String) {
        viewModelScope.launch {
            val newAirport = flightRepository.getAirportsStreamByCondition(code).first()
                .first()
            val listRoutes = flightRepository.getListRoutes(newAirport)
            _uiState.update {
                it.copy(
                    selectedAirport = newAirport,
                    listRoutes = listRoutes,
                    isSearch = false
                )
            }
            updateInputText(newAirport.code)
        }
    }

    private fun updateFalseSearchState() {
        _uiState.update {
            it.copy(
                isSearch = false
            )
        }
    }

    private fun updateInputText(text: String) {
        _uiState.update {
            it.copy(
                inputText = text
            )
        }
    }

    private fun updateListAirports(text: String) {
        viewModelScope.launch {
            val listAirports = flightRepository.getAirportsStreamByCondition(text)
            _uiState.update {
                it.copy(
                    listAirports = listAirports.first(),
                    isSearch = true
                )
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightViewModel(application.container.flightRepository)
            }
        }
    }

}

data class FlightUiState(
    val inputText: String = "",
    val selectedAirport: Airport = Airport(1, "", "", 1),
    val listAirports: List<Airport> = emptyList(),
    val listRoutes: List<Route> = emptyList(),
    val isSearch: Boolean = false
)

