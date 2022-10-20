package com.example.pokedex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.details.DetailsActivity
import com.example.pokedex.home.HomeFragment
import com.example.pokedex.tipes.TypeListActivity
import com.example.pokedex.tipes.TypesFragment

class MainActivity : AppCompatActivity(), HomeFragment.OnPokemonClickListener,
    TypesFragment.OnTypeClickListener {

    private lateinit var navHostFragment: NavHostFragment

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navControler = navHostFragment.navController


        binding.bottonNavigation.apply {
            setupWithNavController(navControler)
        }
    }

    override fun onPokemonClick(poke_name: String) {
        showDetailsActivity(poke_name)
    }

    private fun showDetailsActivity(poke_name: String) {
        DetailsActivity.open(this, poke_name)
    }

    override fun onTypeClick(typeUrl: String) {
        showTypeList(typeUrl)
    }

    //Abre a lista de pokemon por tipos quando for clicado
    private fun showTypeList(typeUrl: String) {
        Log.d("HSV", "Na MainActivity: $typeUrl")

        TypeListActivity.open(this, typeUrl)
    }
}