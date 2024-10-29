package org.setu.rugbyscorebuddy.models


import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class RugbyScoreMemStore : RugbyScoreStore {
    val rugbygames = ArrayList<RugbyScoreModel>()

    override fun findAll(userId: Long): List<RugbyScoreModel> {
        val foundRugbyGames = rugbygames.filter { it.userId == userId }.toMutableList()
        logAll()
        return foundRugbyGames
    }

    override fun findById(id:Long) : RugbyScoreModel? {
        val foundRugbyGame: RugbyScoreModel? = rugbygames.find { it.id == id }
        return foundRugbyGame
    }

    override fun create(rugbygame: RugbyScoreModel) {
        rugbygame.id = getId()
        rugbygames.add(rugbygame)
        logAll()
    }

    override fun update(rugbygame: RugbyScoreModel) {
        var foundRugbyGame: RugbyScoreModel? = rugbygames.find { p -> p.id == rugbygame.id }
        if (foundRugbyGame != null) {
            foundRugbyGame.homeTeam = rugbygame.homeTeam
            foundRugbyGame.homeTeamTries = rugbygame.homeTeamTries
            foundRugbyGame.homeTeamConversions = rugbygame.homeTeamConversions
            foundRugbyGame.homeTeamPenalties = rugbygame.homeTeamPenalties
            foundRugbyGame.awayTeam = rugbygame.awayTeam
            foundRugbyGame.awayTeamTries = rugbygame.awayTeamTries
            foundRugbyGame.awayTeamConversions = rugbygame.awayTeamConversions
            foundRugbyGame.awayTeamPenalties = rugbygame.awayTeamPenalties
            foundRugbyGame.image = rugbygame.image
            foundRugbyGame.lat = rugbygame.lat
            foundRugbyGame.lng = rugbygame.lng
            foundRugbyGame.zoom = rugbygame.zoom

            logAll()
        }
    }

    override fun delete(rugbygame: RugbyScoreModel) {
        rugbygames.remove(rugbygame)
        logAll()
    }

    fun logAll() {
        rugbygames.forEach{ i("${it}") }
    }
}