package org.setu.rugbyscorebuddy.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RugbyScoreModel(
    var id: Long = 0,
    var homeTeam: String = "",
    var awayTeam: String = "") : Parcelable