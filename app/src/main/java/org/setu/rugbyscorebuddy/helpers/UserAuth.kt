package org.setu.rugbyscorebuddy.helpers

import at.favre.lib.crypto.bcrypt.BCrypt
import org.setu.rugbyscorebuddy.models.UserModel
import org.setu.rugbyscorebuddy.models.UserStore

class UserAuth(private val userStore: UserStore, private val sessionManager: SessionManager) {

    fun signUp(email: String, password: String): Boolean {
        // Hash the password before creating the UserModel
        val hashedPassword = hashPassword(password)
        val user = UserModel(email = email, password = hashedPassword)
        return userStore.create(user)
    }

    fun login(email: String, password: String): Boolean {
        val user = userStore.getUser(email) ?: return false
        // Verify the hashed password, If the password is correct
        // set session and return true
        if (verifyPassword(password, user.password)) {
            // Create a session for the logged-in user
            sessionManager.createLoginSession(user.id, user.email)
            return true
        }
        return false
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword)
        return result.verified
    }
}
