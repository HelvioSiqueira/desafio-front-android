package com.example.pokedex.repository.http.model

import com.google.gson.annotations.SerializedName

data class ListTypesGson(
    @SerializedName("results")
    val listTypes: List<PokeType> = arrayListOf()
)

data class PokeType(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)