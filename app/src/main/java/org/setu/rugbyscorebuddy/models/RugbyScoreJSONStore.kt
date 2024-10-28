package org.setu.rugbyscorebuddy.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.setu.rugbyscorebuddy.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "rugbygames.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<RugbyScoreModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class RugbyScoreJSONStore(private val context: Context) : RugbyScoreStore {

    var rugbygames = mutableListOf<RugbyScoreModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RugbyScoreModel> {
        logAll()
        return rugbygames
    }

    override fun findById(id:Long) : RugbyScoreModel? {
        val foundRugbyGame: RugbyScoreModel? = rugbygames.find { it.id == id }
        return foundRugbyGame
    }

    override fun create(rugbygame: RugbyScoreModel) {
        rugbygame.id = generateRandomId()
        rugbygames.add(rugbygame)
        serialize()
    }

    override fun update(rugbygame: RugbyScoreModel) {
        val rugbygamesList = findAll() as ArrayList<RugbyScoreModel>
        var foundRugbyGame: RugbyScoreModel? = rugbygamesList.find { p -> p.id == rugbygame.id }
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
        }
        serialize()
    }

    override fun delete(rugbygame: RugbyScoreModel) {
        rugbygames.remove(rugbygame)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(rugbygames, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        rugbygames = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        rugbygames.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}