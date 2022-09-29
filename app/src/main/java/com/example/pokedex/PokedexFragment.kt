package com.example.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedex.databinding.FragmentPokedexBinding

class PokedexFragment: Fragment() {

    private lateinit var binding: FragmentPokedexBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokedexBinding.inflate(layoutInflater)
        return binding.root
    }
}