package com.example.apidragonballapp.domain.use_case

import com.example.apidragonballapp.Resource.NetworkResult
import com.example.apidragonballapp.data.repository.PlanetRepository
import com.example.apidragonballapp.domain.model.Planet
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(query: String = ""): NetworkResult<List<Planet>> {
        return when (val result = repository.getPlanetsFromApi()) {
            is NetworkResult.Success -> {
                val planets = result.data ?: emptyList()
                if (query.isBlank()) {
                    NetworkResult.Success(planets)
                } else {
                    NetworkResult.Success(planets.filter { it.name.contains(query, ignoreCase = true) })
                }
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message ?: "Error")
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }
}