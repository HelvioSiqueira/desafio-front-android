package com.example.pokedex.abilities

import androidx.lifecycle.ViewModel
import com.example.pokedex.model.Ability
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.repository.http.AbilitiesHttpsUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AbilitiesViewModel(private val repository: PokedexRepository): ViewModel(), KoinComponent {

    private val abilitiesHttps: AbilitiesHttpsUtils by inject()

    suspend fun getAbilityEffects(nameAbility: String): Ability {

        return abilitiesHttps.getShortEfects(nameAbility)
    }
}