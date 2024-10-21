package org.setu.rugbyscorebuddy.models

interface RugbyScoreStore {
    fun findAll(): List<RugbyScoreModel>
    fun create(rugbygame: RugbyScoreModel)
    fun update(rugbygame: RugbyScoreModel)
}