package org.setu.rugbyscorebuddy.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class RugbyScoreMemStore : RugbyScoreStore {
    val rugbygames = ArrayList<RugbyScoreModel>()

    override fun findAll(): List<RugbyScoreModel> {
        return rugbygames
    }

    override fun create(rugbygame: RugbyScoreModel) {
        rugbygames.add(rugbygame)
        logAll()
    }

    override fun update(rugbygame: RugbyScoreModel) {
        var foundRugbyGame: RugbyScoreModel? = rugbygames.find { p -> p.id == rugbygame.id }
        if (foundRugbyGame != null) {
            foundRugbyGame.homeTeam = rugbygame.homeTeam
            foundRugbyGame.awayTeam = rugbygame.awayTeam
            logAll()
        }
    }

    fun logAll() {
        rugbygames.forEach{ i("${it}") }
    }
}