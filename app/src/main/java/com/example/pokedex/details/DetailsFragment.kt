package com.example.pokedex.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokedex.model.PokeList
import com.example.pokedex.model.Pokemon
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailsBinding
import com.example.pokedex.util.URL_IMG
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()

    private lateinit var pokeName: String
    private lateinit var pokemonDetails: Pokemon

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

        Log.d("HSV", pokeName)

        if(pokeName == "deoxys"){
            pokeName = "deoxys-normal"
        }

        lifecycleScope.launch {

            pokemonDetails = viewModel.getPokemon(pokeName)

            fillDetails(binding, pokemonDetails)
            fillStatus(pokemonDetails.stats)
            fillEvolutions(binding, pokemonDetails.evolutionChain)

            viewModel.fillComplete.value = true
        }

        viewModel.fillComplete.observe(viewLifecycleOwner, Observer { fill ->
            if (fill) {
                if (viewModel.checar(pokemonDetails.id.toInt()) == 1) {
                    binding.iconFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    binding.iconFav.isEnabled = false

                } else {
                    binding.iconFav.setOnClickListener {

                        val pokeList = PokeList().apply {
                            id = pokemonDetails.id
                            name = pokemonDetails.name
                            url = ""
                            urlImg = pokemonDetails.sprite
                        }

                        viewModel.salvarNaPokedex(pokeList)

                        binding.iconFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                        binding.iconFav.isEnabled = false
                    }
                }

                viewModel.fillComplete.value = false
            }
        })

        return binding.root
    }

    private fun fillEvolutions(
        binding: FragmentDetailsBinding,
        evolutionChain: List<List<String>>
    ) {
        val arrayLayouts = arrayOf(binding.estagio1, binding.estagio2, binding.estagio3)
        val quantStages = evolutionChain.count { it.isNotEmpty() }

        if (quantStages == 1) {
            binding.txtEvolutions.text = resources.getString(R.string.txt_nao_evolui)
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

        Log.d("HSV", pokemon.toString())

        Glide.with(this@DetailsFragment).load(pokemon.sprite).into(binding.folder)

        binding.apply {
            pokeName.text = resources.getString(R.string.poke_name, pokemon.id, pokemon.name)
            pokeHeight.text = resources.getString(R.string.poke_height, (pokemon.height.toDouble() / 10).toString())
            pokeWeight.text = resources.getString(R.string.poke_weight, (pokemon.weight.toDouble() / 10).toString())
            pokeType.text = resources.getString(
                R.string.poke_types,
                pokemon.types.joinToString(separator = ", ")
            )
        }

        pokemon.abilites.forEach { ability->
            val txtAbility = TextView(requireContext())

            txtAbility.text = ability.replaceFirstChar { it.uppercase() }

            txtAbility.setOnClickListener {
                showAbilitiesFragment(ability)
            }

            binding.listAbilities.addView(txtAbility)
        }
    }

    private fun fillStatus(stats: List<Map<String, Int>>) {

        binding.apply {
            barHp.setTamStatus(stats[0].getValue("hp"))
            barAttack.setTamStatus(stats[1].getValue("attack"))
            barDefense.setTamStatus(stats[2].getValue("defense"))
            barSpAttack.setTamStatus(stats[3].getValue("special-attack"))
            barSpDefense.setTamStatus(stats[4].getValue("special-defense"))
            barSpeed.setTamStatus(stats[5].getValue("speed"))
        }
    }

    private fun showNameEvolution(nameEvolution: String) {
        val formatedName =
            nameEvolution.substringBeforeLast("|").replaceFirstChar { it.uppercase() }

        Toast.makeText(requireContext(), formatedName, Toast.LENGTH_LONG).show()
    }

    private fun showAbilitiesFragment(nameAbility: String){
        if(activity is DetailsActivity){
            val act = activity as DetailsActivity

            act.showAbility(nameAbility)
        }
    }

    companion object{
        const val TAG_DETAILS = "tagDetails"
        private const val EXTRA_POKE_NAME = "poke_name"

        fun newInstance(poke_name: String) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_POKE_NAME, poke_name)
            }
        }
    }
}