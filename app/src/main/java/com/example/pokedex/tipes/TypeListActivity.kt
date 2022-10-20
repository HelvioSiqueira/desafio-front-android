package com.example.pokedex.tipes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityTypeListBinding
import com.example.pokedex.details.DetailsActivity

class TypeListActivity : AppCompatActivity(), ListPokeFragment.OnPokemonClickListener{

    private lateinit var binding: ActivityTypeListBinding

    private val urlType: String by lazy{ intent.getStringExtra(EXTRA_TYPE) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTypeListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        showTypeFragment()
    }

    private fun showTypeFragment(){

        Log.d("HSV", "No TypeListActivity: $urlType")

        val fragment = ListPokeFragment.newInstance(urlType)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_typeList, fragment, ListPokeFragment.TAG_TYPE_LIST)
            .commit()
    }

    override fun onPokemonClick(poke_name: String) {
        showPokemonDetails(poke_name)
    }

    //Abre a tela de detalhes do Pokemon
    private fun showPokemonDetails(poke_name: String){
        DetailsActivity.open(this, poke_name)
    }

    companion object{
        private const val EXTRA_TYPE = "urlType"

        fun open(context: Context, urlType: String){
            context.startActivity(Intent(context, TypeListActivity::class.java).apply {
                putExtra(EXTRA_TYPE, urlType)
            })
        }
    }
}