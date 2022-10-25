package com.example.pokedex.model

data class Pokemon(
    var name: String = "",
    var id: String = "",
    var height: String = "",
    var weight: String = "",
    var types: List<String> = arrayListOf(),
    var stats: List<Map<String, Int>> = arrayListOf(),
    var abilites: List<String> = arrayListOf(),
    var evolvesFrom: String? = "",
    var evolutionChain: MutableList<MutableList<String>> = mutableListOf(),
    var sprite: String = ""
)
