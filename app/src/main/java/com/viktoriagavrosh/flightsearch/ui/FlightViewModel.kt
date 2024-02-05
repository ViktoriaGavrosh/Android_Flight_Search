package com.viktoriagavrosh.flightsearch.ui


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
        viewModelScope.launch {
            if (text.isEmpty()) {
                val listFavoriteRoutes = flightRepository.getFavoriteRoutes()
                _uiState.update {
                    it.copy(
                        inputText = text,
                        listFavoriteRoutes = listFavoriteRoutes,
                        isSearch = false
                    )
                }
            } else {
                val listAirports = flightRepository.getAirportsStreamByCondition(text)
                _uiState.update {
                    it.copy(
                        inputText = text,
                        listAirports = listAirports.first(),
                        isSearch = true
                    )
                }
            }
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
                    inputText = newAirport.code,
                    selectedAirport = newAirport,
                    listRoutes = listRoutes,
                    isSearch = false
                )
            }
        }
    }

    fun updateRoute(route: Route) {
        route.changeIsFavorite()
        if (route.isFavorite) {
            addFavoriteRoute(route)
        } else {
            deleteFavoriteRoute(route)
        }
        updateListFavoriteRoutes()
    }

    private fun updateListFavoriteRoutes() {
        viewModelScope.launch {
            val listFavoriteRoutes = flightRepository.getFavoriteRoutes()
            _uiState.update {
                it.copy(
                    listFavoriteRoutes = listFavoriteRoutes
                )
            }
        }
    }


    private fun deleteFavoriteRoute(route: Route) {
        viewModelScope.launch {
            flightRepository.deleteFavoriteRoute(route)
        }
    }

    private fun addFavoriteRoute(route: Route) {
        viewModelScope.launch {
            flightRepository.insertFavoriteRoute(route)
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
    val listFavoriteRoutes: List<Route> = emptyList(),
    val isSearch: Boolean = false
)

