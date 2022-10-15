package com.example.pokedex.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailsBinding
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
            viewModel.getPokemon(pokeName).observe(viewLifecycleOwner, Observer {
                Log.d("HSV", it.toString())

                Glide.with(this@DetailsFragment).load(it.sprite).into(binding.folder)
                binding.pokeName.text = resources.getString(R.string.poke_name, it.id, it.name)
                binding.pokeHeight.text = resources.getString(R.string.poke_height, it.height)
                binding.pokeWeight.text = resources.getString(R.string.poke_weight, it.weight)
                binding.pokeType.text = resources.getString(
                    R.string.poke_types,
                    it.types.joinToString(separator = ", ")
                )
                binding.pokeAbilites.text = resources.getString(
                    R.string.poke_abilites,
                    it.abilites.joinToString(separator = ", ")
                )

                //stats=[{hp=45}, {attack=49}, {defense=49}, {special-attack=65}, {special-defense=65}, {speed=45}]

                binding.barHp.setTamStatus(it.stats[0].getValue("hp") / 10)
                binding.barAttack.setTamStatus(it.stats[1].getValue("attack") / 10)
                binding.barDefense.setTamStatus(it.stats[2].getValue("defense") / 10)
                binding.barSpAttack.setTamStatus(it.stats[3].getValue("special-attack") / 10)
                binding.barSpDefense.setTamStatus(it.stats[4].getValue("special-defense") / 10)
                binding.barSpeed.setTamStatus(it.stats[5].getValue("speed") / 10)

                var maxEvolutions = 0

                it.evolutionChain.forEach { evo ->
                    evo.run {
                        if (count() > maxEvolutions) {
                            maxEvolutions = count()
                        }
                    }
                }

                val quantStages = it.evolutionChain.count { it.isNotEmpty() }


                val first = "$URL_IMG${it.evolutionChain[0][0].substringAfterLast("|")}"

                val arrayLayouts = arrayOf(binding.estagio1, binding.estagio2, binding.estagio3)

                for (x in 0 until quantStages) {

                    val img = ImageView(requireContext())

                    Glide.with(this@DetailsFragment)
                        .load("$URL_IMG${it.evolutionChain[x][0].substringAfterLast("|")}.png")
                        .apply(RequestOptions.overrideOf(250, 250)).into(img)

                    arrayLayouts[x].addView(img)
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

                Log.d("HSV", "$maxEvolutions, $quantStages, $first")


            })
        }

        return binding.root
    }
}