package com.example.pokedex.details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.max

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

                var max = 0

                it.evolutionChain.forEach { evo ->
                    evo.run {
                        if (count() > max) {
                            max = count()
                        }
                    }
                }
            })
        }

        return binding.root
    }
}