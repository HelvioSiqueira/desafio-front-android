package com.example.pokedex.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedex.R
import com.example.pokedex.abilities.AbilitiesFragment
import com.example.pokedex.databinding.ActivityDetailsBinding
import com.example.pokedex.repository.http.model.NameAbility

class DetailsActivity : AppCompatActivity(), AbilitiesFragment.OnShowAbility {

    private lateinit var binding: ActivityDetailsBinding

    private val poke_name: String by lazy { intent.getStringExtra(EXTRA_POKE_NAME) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        showDetailsFragment()
    }

    private fun showDetailsFragment(){
        val fragment = DetailsFragment.newInstance(poke_name)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_details, fragment, DetailsFragment.TAG_DETAILS)
            .commit()
    }

    override fun showAbility(nameAbility: String) {
        AbilitiesFragment.newInstance(nameAbility).open(supportFragmentManager)
    }

    companion object{
        private const val EXTRA_POKE_NAME = "poke_name"

        fun open(context: Context, poke_name: String){
            context.startActivity(Intent(context, DetailsActivity::class.java).apply {
                putExtra(EXTRA_POKE_NAME, poke_name)
            })
        }
    }
}