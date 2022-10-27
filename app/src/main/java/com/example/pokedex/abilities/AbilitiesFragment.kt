package com.example.pokedex.abilities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.FragmentAbilitiesBinding
import com.example.pokedex.model.Ability
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AbilitiesFragment: DialogFragment() {

    private lateinit var binding: FragmentAbilitiesBinding

    private val viewModel: AbilitiesViewModel by inject()

    var nameAbility: String = ""

    private lateinit var ability: Ability

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAbilitiesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run {
            nameAbility = getString(EXTRA_ABILITY).toString()
        }

        lifecycleScope.launch {
            ability = viewModel.getAbilityEfects(nameAbility)
            fillAbilitiesFragment(ability, binding)
        }
    }

    private fun fillAbilitiesFragment(ability: Ability, binding: FragmentAbilitiesBinding){
        binding.titleAbility.text = ability.name.replaceFirstChar { it.uppercase() }
        binding.effect.text = ability.efect
        binding.shortEffect.text = ability.shortEfect
    }

    fun open(fm: FragmentManager){
        if(fm.findFragmentByTag(DIALOG_TAG) == null){
            show(fm, DIALOG_TAG)
        }
    }

    interface OnShowAbility{
        fun showAbility(nameAbility: String)
    }

    companion object{

        private const val DIALOG_TAG = "abilityDialog"
        private const val EXTRA_ABILITY = "ability"

        fun newInstance(nameAbility: String) = AbilitiesFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_ABILITY, nameAbility)
            }
        }
    }
}