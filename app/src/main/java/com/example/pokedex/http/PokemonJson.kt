package com.example.pokedex.http

import android.util.Log
import com.example.pokedex.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonJson(
    @SerializedName("name")
    var _name: String = "",
    @SerializedName("id")
    val _id: String = "",
    @SerializedName("height")
    val _height: String = "",
    @SerializedName("weight")
    val _weight: String = "",
    @SerializedName("types")
    val _types: List<Type> = arrayListOf(),
    @SerializedName("stats")
    val stats: List<BaseStat>
){

}

data class Type(
    @SerializedName("type")
    val type: NameType
)

data class NameType(
    @SerializedName("name")
    val nameType: String = ""
)

data class BaseStat(
    @SerializedName("base_stat")
    val base_stat: Int = 0,
    @SerializedName("stat")
    val stat: NameStat
)

data class NameStat(
    @SerializedName("name")
    val nameStat: String = ""
)