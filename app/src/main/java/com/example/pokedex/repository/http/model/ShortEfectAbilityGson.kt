package com.example.pokedex.repository.http.model

import com.google.gson.annotations.SerializedName

data class ShortEfectAbilityGson(
    @SerializedName("name")
    val nameAbility: String = "",
    @SerializedName("effect_entries")
    val effectEntry: List<ShortEfect> = arrayListOf()
)

data class ShortEfect(
    @SerializedName("effect")
    val effect: String = "",
    @SerializedName("short_effect")
    val shortEffect: String = ""
)
