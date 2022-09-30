package com.example.pokedex

data class PokeList(
    var name: String = "",
    var url: String = ""
){
    init {
        name = name.replaceFirstChar { it.uppercase() }
    }
}
