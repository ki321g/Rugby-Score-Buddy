package org.setu.rugbyscorebuddy.models

interface RugbyScoreStore {
    fun findAll(): List<RugbyScoreModel>
    fun findById(id:Long) : RugbyScoreModel?
    fun create(rugbygame: RugbyScoreModel)
    fun update(rugbygame: RugbyScoreModel)
    fun delete(rugbygame: RugbyScoreModel)
}