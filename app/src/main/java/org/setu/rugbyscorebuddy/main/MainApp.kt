package org.setu.rugbyscorebuddy.main

import android.app.Application
import org.setu.rugbyscorebuddy.models.RugbyScoreStore
import org.setu.rugbyscorebuddy.models.RugbyScoreMemStore
import org.setu.rugbyscorebuddy.models.RugbyScoreJSONStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var rugbygames: RugbyScoreStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        rugbygames = RugbyScoreJSONStore(applicationContext)
        i("RugbyScoreBuddy Started...")
    }
}