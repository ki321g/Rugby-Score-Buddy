package org.setu.rugbyscorebuddy.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.setu.rugbyscorebuddy.helpers.SessionManager

//class RugbyScoreViewModel: ViewModel() {
class RugbyScoreViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var sessionManager: SessionManager
    private val _isReady = MutableStateFlow(false)
    private val _isLoggedIn = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    val isLoggedIn = _isLoggedIn.asStateFlow()


    init {
        sessionManager = SessionManager(application.applicationContext)
        viewModelScope.launch {
            //Can check if user is authenticated here
            if (sessionManager.isLoggedIn()) {
                // Redirect to main screen if already logged in
//                delay(2000L)
                _isReady.value = true
                _isLoggedIn.value = true
            } else {
                delay(2000L)
                _isReady.value = true
            }
        }
    }
}