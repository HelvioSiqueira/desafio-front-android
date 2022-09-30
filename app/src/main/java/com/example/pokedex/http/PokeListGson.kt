package com.example.pokedex.http

import com.google.gson.annotations.SerializedName

data class PokeListGson(
    @SerializedName("results")
    val results: List<Result> = arrayListOf()
)

data class Result(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)
