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
    val description: String,
    val image: String
)
@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "items") val items: List<SimpleCharacterDto>
)

    @JsonClass(generateAdapter = true)
data class SimpleCharacterDto(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val affiliation: String
)

interface PlanetApi {
    @GET("planets")
    suspend fun getPlanets(): PlanetResponse

    @GET("characters")
    suspend fun getCharacters(): CharacterResponse
}