package com.example.pokedex.repository.http.model

import com.google.gson.annotations.SerializedName

data class PokeEvolution(
    @SerializedName("evolution_chain")
    val _evolutionChain: EvolutionChainUrl,
    @SerializedName("evolves_from_species")
    val _evolvesFrom: EvolvesFromName? = null
)

data class EvolutionChainUrl(
    @SerializedName("url")
    val url: String = ""
)

data class EvolvesFromName(
    @SerializedName("name")
    val name: String = ""
)
