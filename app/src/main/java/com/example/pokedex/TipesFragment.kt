package com.example.pokedex

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedex.databinding.FragmentTipesBinding

class TipesFragment: Fragment() {

    private lateinit var binding: FragmentTipesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTipesBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("HSV", "TipesFragment startado")
    }

}