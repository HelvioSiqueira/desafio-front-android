package com.example.pokedex.repository.http.model

import com.google.gson.annotations.SerializedName

data class PokeEvolutionChain(
    @SerializedName("chain")
    val chain: Chain
)

data class Chain(
    @SerializedName("evolves_to")
    val evolvesToFirst: List<EvolvesToFirst>? = null,
    @SerializedName("species")
    val species: SpeciesName
)

data class EvolvesToFirst(
    @SerializedName("evolves_to")
    val evolvesToSecond: List<EvolvesToSecond>? = null,
    @SerializedName("species")
    val species: SpeciesName
)

data class EvolvesToSecond(
    @SerializedName("evolves_to")
    val evolveToThird: List<EvolveToThird>? = null,
    @SerializedName("species")
    val species: SpeciesName
)

data class EvolveToThird(
    @SerializedName("species")
    val species: SpeciesName
)

data class SpeciesName(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)