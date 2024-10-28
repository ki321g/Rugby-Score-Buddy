package org.setu.rugbyscorebuddy.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RugbyScoreModel(
    var id: Long = 0,
    var homeTeam: String = "",
    var homeTeamTries: Int = 0,
    var homeTeamConversions: Int = 0,
    var homeTeamPenalties: Int = 0,
    var awayTeam: String = "",
    var awayTeamTries: Int = 0,
    var awayTeamConversions: Int = 0,
    var awayTeamPenalties: Int = 0,
    var image: Uri = Uri.EMPTY,
    var lat : Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

// New User data class
@Parcelize
data class UserModel(
    var id: Long = 0,
    var email: String = "",
    var password: String = ""
) : Parcelable