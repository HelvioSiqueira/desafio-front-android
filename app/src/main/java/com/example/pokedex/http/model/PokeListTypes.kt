package com.example.pokedex.http.model

import com.google.gson.annotations.SerializedName

data class PokeListTypes(
    @SerializedName("pokemon")
    val pokeListTypes: List<TypePoke1> = arrayListOf()
)

data class TypePoke1(
    @SerializedName("pokemon")
    val pokemon: TypePoke2
)

data class TypePoke2(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)
