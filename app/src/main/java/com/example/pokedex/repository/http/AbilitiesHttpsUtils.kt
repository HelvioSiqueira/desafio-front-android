package com.example.pokedex.repository.http

import android.util.Log
import com.example.pokedex.model.Ability

class AbilitiesHttpsUtils(private val api: Endpoint) {

    suspend fun getShortEfects(nameEfect: String): Ability {
        val response = api.getShortEfectsAbilities(nameEfect)

        val ability = Ability()

        if (response.isSuccessful) {

            response.body()?.let {

                Log.d("HSV", it.effectEntry.toString())

                if (it.effectEntry.isNotEmpty()) {
                    if (it.effectEntry.size > 1) {
                        ability.name = it.nameAbility
                        ability.efect = it.effectEntry[1].efect
                        ability.shortEfect = it.effectEntry[1].shortEfect
                    } else {
                        ability.name = it.nameAbility
                        ability.efect = it.effectEntry[0].efect
                        ability.shortEfect = it.effectEntry[0].shortEfect
                    }
                }
            }
        }

        return ability
    }
}