package org.setu.rugbyscorebuddy.main

import android.app.Application
import org.setu.rugbyscorebuddy.models.RugbyScoreModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val rugbygames = ArrayList<RugbyScoreModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("RugbyScoreBuddy Started...")
        rugbygames.add(RugbyScoreModel("Waterford City", "Tramore"))
        rugbygames.add(RugbyScoreModel("Clonmel", "Waterpark"))
        rugbygames.add(RugbyScoreModel("Carrick", "Cork Constitution"))
    }
}