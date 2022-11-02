package com.example.pokedex.repository.http

import com.example.pokedex.model.Ability

class AbilitiesHttpsUtils(private val api: Endpoint) {

    suspend fun getShortEfects(nameEfect: String): Ability {
        val response = api.getShortEfectsAbilities(nameEfect)

        val ability = Ability()

        if (response.isSuccessful) {

            response.body()?.let { shortEffectAbility->

                if (shortEffectAbility.effectEntry.isNotEmpty()) {
                    if (shortEffectAbility.effectEntry.size > 1) {

                        ability.apply {
                            name = shortEffectAbility.nameAbility
                            effect = shortEffectAbility.effectEntry[1].effect
                            ability.shortEffect = shortEffectAbility.effectEntry[1].shortEffect
                        }

                    } else {
                        ability.apply {
                            name = shortEffectAbility.nameAbility
                            effect = shortEffectAbility.effectEntry[0].effect
                            ability.shortEffect = shortEffectAbility.effectEntry[0].shortEffect
                        }
                    }
                }
            }
        }

        return ability
    }
}