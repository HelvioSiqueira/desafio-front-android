package com.example.pokedex.repository.http

import com.example.pokedex.model.Ability

class AbilitiesHttpsUtils(private val api: Endpoint) {

    suspend fun getShortEfects(nameEfect: String): Ability {
        val response = api.getShortEfectsAbilities(nameEfect)

        val ability = Ability()

        if(response.isSuccessful){

            response.body()?.let {
                ability.name = it.nameAbility
                ability.efect = it.effectEntry[1].efect
                ability.shortEfect = it.effectEntry[1].shortEfect
            }
        }

        return ability
    }
}