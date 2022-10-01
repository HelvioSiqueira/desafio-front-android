package com.example.pokedex

data class Pokemon(
    var name: String = "",
    var id: String = "",
    var height: String = "",
    var weight: String = "",
    var types: List<String> = arrayListOf(),
    var stats: List<Map<String, Int>> = arrayListOf()
)
