package org.setu.rugbyscorebuddy.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel): Boolean
    fun getUser(email: String): UserModel?
    fun isValidUser(email: String, password: String): Boolean
}

