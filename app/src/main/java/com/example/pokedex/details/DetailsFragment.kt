package com.example.pokedex.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment:Fragment() {

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

            binding.pokeName.text = pokeName.toString().uppercase()
        }

        lifecycleScope.launch {
            viewModel.getPokemon(pokeName).observe(viewLifecycleOwner, Observer {
                Log.d("HSV", it.toString())
            })
        }

        return binding.root


    }
}