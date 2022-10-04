package com.example.pokedex.home

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.PokeList
import com.example.pokedex.http.PokeListGson
import com.example.pokedex.repository.PokeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: PokeRepository): ViewModel() {

    val pokeList = MutableLiveData<MutableList<PokeList>>()
    val error = MutableLiveData<Boolean>()

    private suspend fun getPokeList() {

        val callback = repository.pokeList()


        callback.enqueue(object : Callback<PokeListGson> {
            override fun onFailure(call: Call<PokeListGson>, t: Throwable) {
                error.value = true
            }

            override fun onResponse(call: Call<PokeListGson>, response: Response<PokeListGson>) {

                response.body()?.results?.forEach {
                    val poke = PokeList()
                    poke.name = it.name
                    poke.url = it.url

                    pokeList.value?.add(poke)
                }
            }
        })
    }

    private fun handleResponse(response: Response<PokeListGson>) {

        if (response.isSuccessful) {
            response.body()?.results?.forEach {
                val poke = PokeList()
                poke.name = it.name
                poke.url = it.url

                pokeList.value?.add(poke)
            }
        }
    }
}