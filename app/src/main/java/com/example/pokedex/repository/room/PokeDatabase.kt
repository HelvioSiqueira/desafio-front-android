package com.example.pokedex.repository.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class PokeDatabase : RoomDatabase() {

    abstract fun pokeDao(): PokeDao

    companion object {
        private var instance: PokeDatabase? = null

        fun getDatabase(context: Context): PokeDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokeDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return instance as PokeDatabase
        }
    }
}