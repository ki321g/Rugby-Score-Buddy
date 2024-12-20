package org.setu.rugbyscorebuddy.main

import android.app.Application
import org.setu.rugbyscorebuddy.models.RugbyScoreStore
import org.setu.rugbyscorebuddy.models.RugbyScoreJSONStore
import org.setu.rugbyscorebuddy.models.UserJSONStore
import org.setu.rugbyscorebuddy.models.UserStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var rugbygames: RugbyScoreStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        rugbygames = RugbyScoreJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        i("RugbyScoreBuddy Started...")
    }
}