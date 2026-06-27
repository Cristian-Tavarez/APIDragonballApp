package com.example.apidragonballapp.Resource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apidragonballapp.domain.model.Character
import com.example.apidragonballapp.domain.use_case.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    var uiState by mutableStateOf<NetworkResult<List<Character>>>(NetworkResult.Loading())
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        searchCharacters()
    }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
        searchCharacters()
    }

    private fun searchCharacters() {
        viewModelScope.launch {
            uiState = NetworkResult.Loading()
            uiState = getCharactersUseCase(searchQuery)
        }
    }
}