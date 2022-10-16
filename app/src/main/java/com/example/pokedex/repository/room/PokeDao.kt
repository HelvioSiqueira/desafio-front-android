package com.example.pokedex.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex.Pokemon

@Dao
interface PokeDao {
    @Insert
    fun insert(pokemon: Pokemon): Long

    @Delete
    fun delete(vararg pokemonExcluidos: List<Pokemon>): Long

    @Query("""SELECT * FROM $TABLE_POKEDEX""")
    fun search(): LiveData<List<Pokemon>>

    @Query("""SELECT CASE WHEN EXISTS(SELECT * FROM $TABLE_POKEDEX WHERE $COLUMN_ID = :id) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END""")
    fun pokemonById(id: Int): Int
}