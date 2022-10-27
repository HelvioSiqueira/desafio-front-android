package com.example.pokedex.repository.http.model

import com.google.gson.annotations.SerializedName

data class PokemonGson(
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
    val _stats: List<BaseStat> = arrayListOf(),
    @SerializedName("abilities")
    val _abilities: List<Ability> = arrayListOf(),
    @SerializedName("sprites")
    val _sprites: Other
)

//Pega a String contendo o tipo do pokemon
data class Type(
    @SerializedName("type")
    val type: NameType
)

data class NameType(
    @SerializedName("name")
    val nameType: String = ""
)

//--------------------------------------

//Pega o Int com status base e nome do status
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

//--------------------------------------

//Pega o nome das habilidades
data class Ability(
    @SerializedName("ability")
    val ability: NameAbility
)

data class NameAbility(
    @SerializedName("name")
    val nameAbility: String = "",
    @SerializedName("url")
    val urlAbility: String = ""
)

//--------------------------------------

//Pega o sprite official
data class Other(
    @SerializedName("other")
    val officilArtworK: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("official-artwork")
    val frontDefault: FrontDefault
)

data class FrontDefault(
    @SerializedName("front_default")
    val url: String = ""
)

