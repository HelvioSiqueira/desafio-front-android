package com.example.pokedex.http

import com.google.gson.annotations.SerializedName

data class PokemonJson(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("height")
    val height: String = "",
    @SerializedName("weight")
    val weight: String = "",
    //val types: List<Type> = arrayListOf(),
    //val stats: List<BaseStat> = arrayListOf()
)

data class Type(
    val nameType: String = ""
)

data class BaseStat(
    val nameStat: Stat,
    val base_stat: Int = 0
)

data class Stat(
    val nameStat: String = ""
)