package com.example.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex.repository.room.TABLE_POKEDEX

@Entity(tableName = TABLE_POKEDEX)
data class PokeList(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var url: String = "",
    var urlImg: String = ""
)