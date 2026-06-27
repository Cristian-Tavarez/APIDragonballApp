package com.example.apidragonballapp.domain.use_case

import com.example.apidragonballapp.Resource.NetworkResult
import com.example.apidragonballapp.data.repository.PlanetRepository
import com.example.apidragonballapp.domain.model.Character
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(query: String = ""): NetworkResult<List<Character>> {
        return when (val result = repository.getCharactersFromApi()) {
            is NetworkResult.Success -> {
                val characters = result.data ?: emptyList()
                if (query.isBlank()) {
                    NetworkResult.Success(characters)
                } else {
                    NetworkResult.Success(characters.filter { it.name.contains(query, ignoreCase = true) })
                }
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message ?: "Error")
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }
}