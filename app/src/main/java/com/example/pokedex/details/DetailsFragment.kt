package com.example.pokedex.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokedex.Pokemon
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailsBinding
import com.example.pokedex.http.model.PokeEvolutionChain
import com.example.pokedex.util.URL_IMG
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()

    private lateinit var pokeName: String

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsBinding.inflate(layoutInflater)

        arguments?.run {
            pokeName = getString("poke_name").toString()
        }

        lifecycleScope.launch {
            viewModel.getPokemon(pokeName).observe(viewLifecycleOwner, Observer { pokemon ->
                Log.d("HSV", pokemon.toString())

                fillDetails(binding, pokemon)
                fillStatus(pokemon.stats)
                fillEvolutions(binding, pokemon.evolutionChain)
            })
        }

        return binding.root
    }

    private fun fillEvolutions(binding: FragmentDetailsBinding, evolutionChain: List<List<String>>){
        val arrayLayouts = arrayOf(binding.estagio1, binding.estagio2, binding.estagio3)
        val quantStages = evolutionChain.count { it.isNotEmpty() }

        if(quantStages == 1){
            binding.txtEvolutions.text = "Este pokemon nao evolui"
        }

        for ((index, element) in evolutionChain.withIndex()) {
            for (y in 0 until element.count()) {
                val img = ImageView(requireContext())

                img.setOnClickListener {
                    showNameEvolution(evolutionChain[index][y])
                }

                Glide.with(this@DetailsFragment)
                    .load("$URL_IMG${evolutionChain[index][y].substringAfterLast("|")}.png")
                    .apply(RequestOptions.overrideOf(250, 250)).into(img)

                arrayLayouts[index].addView(img)
            }
        }

        when (quantStages) {
            1 -> {
                binding.estagio2.visibility = View.GONE
                binding.estagio3.visibility = View.GONE
            }

            2 -> {
                binding.estagio3.visibility = View.GONE
            }
        }
    }

    private fun fillDetails(binding: FragmentDetailsBinding, pokemon: Pokemon) {
        Glide.with(this@DetailsFragment).load(pokemon.sprite).into(binding.folder)
        binding.pokeName.text =
            resources.getString(R.string.poke_name, pokemon.id, pokemon.name)
        binding.pokeHeight.text = resources.getString(R.string.poke_height, pokemon.height)
        binding.pokeWeight.text = resources.getString(R.string.poke_weight, pokemon.weight)
        binding.pokeType.text = resources.getString(
            R.string.poke_types,
            pokemon.types.joinToString(separator = ", ")
        )
        binding.pokeAbilites.text = resources.getString(
            R.string.poke_abilites,
            pokemon.abilites.joinToString(separator = ", ")
        )
    }

    private fun fillStatus(stats: List<Map<String, Int>>) {

        binding.barHp.setTamStatus(stats[0].getValue("hp") / 10)
        binding.barAttack.setTamStatus(stats[1].getValue("attack") / 10)
        binding.barDefense.setTamStatus(stats[2].getValue("defense") / 10)
        binding.barSpAttack.setTamStatus(stats[3].getValue("special-attack") / 10)
        binding.barSpDefense.setTamStatus(stats[4].getValue("special-defense") / 10)
        binding.barSpeed.setTamStatus(stats[5].getValue("speed") / 10)
    }

    private fun showNameEvolution(nameEvolution: String) {
        val formatedName =
            nameEvolution.substringBeforeLast("|").replaceFirstChar { it.uppercase() }

        Toast.makeText(requireContext(), formatedName, Toast.LENGTH_LONG).show()
    }
}