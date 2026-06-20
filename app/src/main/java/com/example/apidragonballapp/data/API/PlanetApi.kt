package com.example.apidragonballapp.data.API

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET

@JsonClass(generateAdapter = true)
data class PlanetResponse(
    @Json(name = "items") val items: List<SimplePlanetDto>
)

@JsonClass(generateAdapter = true)
data class SimplePlanetDto(
    val id: Int,
    val name: String,
    val description: String
)

interface PlanetApi {
    @GET("planets")
    suspend fun getPlanets(): PlanetResponse
}