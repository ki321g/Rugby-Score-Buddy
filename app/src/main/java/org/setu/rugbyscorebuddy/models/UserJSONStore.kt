package org.setu.rugbyscorebuddy.models

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import org.setu.rugbyscorebuddy.helpers.exists
import org.setu.rugbyscorebuddy.helpers.read
import org.setu.rugbyscorebuddy.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.ArrayList
import java.util.Random

const val USER_JSON_FILE = "users.json"
val userGSONBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UserUriParser())
    .create()
val userListType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateUserRandomId(): Long {
    return Random().nextLong()
}

class UserJSONStore(private val context: Context) : UserStore {

    var users = mutableListOf<UserModel>()

    override fun findAll(): MutableList<UserModel> {
        logAll()
        return users
    }

    override fun create(user: UserModel): Boolean {
        user.id = generateUserRandomId()

        if (users.any { it.email == user.email }) {
            return false // Email already exists
//            Timber.i("User with email ${user.email} already exists")
//            Toast.makeText(context, "User with email ${user.email} already exists", Toast.LENGTH_SHORT).show()
        }

        users.add(user)
        serialize()
        return true
    }

    override fun getUser(email: String): UserModel? {
        val foundUser: UserModel? = users.find { it.email == email }
        return foundUser
    }

    override fun isValidUser(email: String, password: String): Boolean {
        val user = getUser(email)
        return user?.password == password
    }

    init {
        if (exists(context, USER_JSON_FILE)) {
            deserialize()
        }
    }

    private fun serialize() {
        val jsonString = userGSONBuilder.toJson(users, userListType)
        write(context, USER_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, USER_JSON_FILE)
        users = userGSONBuilder.fromJson(jsonString, userListType)
    }

    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }
}

class UserUriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
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