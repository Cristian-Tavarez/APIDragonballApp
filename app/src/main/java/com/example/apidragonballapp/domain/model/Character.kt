package com.example.apidragonballapp.domain.model

data class Character(
    val id: Int,
    val name: String,
    val ki: String,
    val race: String,
    val description: String,
    val image: String
)