package com.viktoriagavrosh.flightsearch.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viktoriagavrosh.flightsearch.FlightSearchApplication
import com.viktoriagavrosh.flightsearch.data.FlightRepository
import com.viktoriagavrosh.flightsearch.data.UserPreferencesRepository
import com.viktoriagavrosh.flightsearch.model.Route
import com.viktoriagavrosh.flightsearch.model.database.Airport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch {
            val inputText = userPreferencesRepository.searchText.first()
            updateInputTextForSearch(inputText)
        }
    }

    fun updateInputTextForSearch(text: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveSearchTextPreferences(text)
            if (text.isEmpty()) {
                updateListFavoriteRoutes(text)
            } else {
               updateListAirports(text)
            }
        }
    }

    fun updateListRoutes(code: String) {
        viewModelScope.launch {
            val newAirport = flightRepository.getAirportsStreamByCondition(code).first()
                .first()
            val listRoutes = flightRepository.getListRoutes(newAirport)
            userPreferencesRepository.saveSearchTextPreferences(newAirport.code)
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
        viewModelScope.launch {
            route.changeIsFavorite()
            if (route.isFavorite) {
                if (!flightRepository.isContainFavoriteRoute(route)) {
                    flightRepository.insertFavoriteRoute(route)
                }
            } else {
                flightRepository.deleteFavoriteRoute(route)
            }
            updateOnlyListFavoriteRoutes()
        }
    }

    private suspend fun updateListFavoriteRoutes(searchText: String) {
        val listFavoriteRoutes = flightRepository.getFavoriteRoutes()
        _uiState.update {
            it.copy(
                inputText = searchText,
                listFavoriteRoutes = listFavoriteRoutes,
                isSearch = false
            )
        }
    }

    private suspend fun updateListAirports(searchText: String) {
        val listAirports = flightRepository.getAirportsStreamByCondition(searchText)
        _uiState.update {
            it.copy(
                inputText = searchText,
                listAirports = listAirports.first(),
                isSearch = true
            )
        }
    }

    private suspend fun updateOnlyListFavoriteRoutes() {
        val listFavoriteRoutes = flightRepository.getFavoriteRoutes()
        _uiState.update {
            it.copy(
                listFavoriteRoutes = listFavoriteRoutes
            )
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightViewModel(
                    application.container.flightRepository,
                    application.container.userPreferencesRepository
                )
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

