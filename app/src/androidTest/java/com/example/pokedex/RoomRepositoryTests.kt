package com.example.pokedex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedex.model.PokeList
import com.example.pokedex.repository.room.PokeDatabase
import com.example.pokedex.repository.room.RoomRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class RoomRepositoryTests {

    @Rule
    @JvmField
            /*
            Para funcionar corretamente e não dá error relacionado a background task
            precisa que seja importado o androidx.arch...InstantTaskExecutorRule
            ou inves do android.arch...InstantTaskExecutorRule
            */
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: RoomRepository
    private lateinit var dummyPokemon: PokeList

    @Before
    fun before_each_test() {
        val db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            PokeDatabase::class.java
        ).build()

        repo = RoomRepository(db)

        dummyPokemon = PokeList(
            id = "132",
            name = "Ditto",
            url = "https://pokeapi.co/api/v2/pokemon/ditto",
            urlImg = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png"
        )
    }

    @Test
    fun load_all_inserted_pokemon() {
        val allPokemon = listOf(newPokemon(), newPokemon(), newPokemon())

        allPokemon.forEach {
            repo.salvar(it)
        }

        val returnedList = LiveDataTestUtil.getValue(repo.buscar())

        assertEquals(allPokemon.size, returnedList?.size)
        assert(returnedList!!.containsAll(allPokemon))
    }

    @Test
    fun load_pokemon_inserted() {
        val pokemon =
            PokeList(id = "1", name = "Buba", "www.algo.com.br", urlImg = "www.algo.com.br")

        repo.salvar(pokemon)

        val returnedPokemon = repo.existeNaPokedex(1)

        val returnedBool = returnedPokemon == 1

        assert(returnedBool)
    }

    @Test
    fun delete_all_pokemon_selected(){
        val allPokemon = mutableListOf(newPokemon(), newPokemon(), newPokemon())

        val pokemonUnique = newPokemon()

        allPokemon.forEach {
            repo.salvar(it)
        }

        repo.salvar(pokemonUnique)

        repo.excluir(*allPokemon.toTypedArray())

        val returnedPokemon = LiveDataTestUtil.getValue(repo.buscar())

        assertFalse(returnedPokemon!!.containsAll(allPokemon))
        assertTrue(returnedPokemon.contains(pokemonUnique))
    }

    private fun newPokemon() =
        PokeList(
            id = UUID.randomUUID().toString(),
            name = UUID.randomUUID().toString(),
            url = UUID.randomUUID().toString(),
            urlImg = UUID.randomUUID().toString()
        )
}