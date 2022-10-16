package com.example.pokedex.repository.http.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokeListGson(
    @SerializedName("results")
    val results: List<Result> = arrayListOf()
): Serializable

data class Result(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)
