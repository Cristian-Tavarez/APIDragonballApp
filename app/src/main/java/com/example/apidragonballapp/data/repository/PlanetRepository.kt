package com.example.apidragonballapp.data.repository

import com.example.apidragonballapp.Resource.NetworkResult
import com.example.apidragonballapp.data.API.PlanetApi
import com.example.apidragonballapp.domain.model.Planet
import javax.inject.Inject

class PlanetRepository @Inject constructor(
    private val api: PlanetApi
) {
    suspend fun getPlanetsFromApi(): NetworkResult<List<Planet>> {
        return try {
            val response = api.getPlanets()
            val domainPlanets = response.items.map { dto ->
                Planet(id = dto.id, name = dto.name, description = dto.description)
            }
            NetworkResult.Success(domainPlanets)
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage ?: "Error de conexión")
        }
    }
}