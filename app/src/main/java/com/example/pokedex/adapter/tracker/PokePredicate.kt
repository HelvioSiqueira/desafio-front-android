package com.example.pokedex.adapter.tracker

import androidx.recyclerview.selection.SelectionTracker

class PokePredicate : SelectionTracker.SelectionPredicate<Long>() {
    override fun canSetStateForKey(key: Long, nextState: Boolean) = true

    override fun canSetStateAtPosition(position: Int, nextState: Boolean) = true

    override fun canSelectMultiple() = true
}