package com.example.apidragonballapp.Resource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apidragonballapp.domain.model.Planet
import com.example.apidragonballapp.domain.use_case.GetPlanetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase
) : ViewModel() {

    var uiState by mutableStateOf<NetworkResult<List<Planet>>>(NetworkResult.Loading())
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        searchPlanets()
    }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
        searchPlanets()
    }

    private fun searchPlanets() {
        viewModelScope.launch {
            uiState = NetworkResult.Loading()
            uiState = getPlanetsUseCase(searchQuery)
        }
    }
}