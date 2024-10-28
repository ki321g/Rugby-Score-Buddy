package org.setu.rugbyscorebuddy.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import org.setu.rugbyscorebuddy.R
import org.setu.rugbyscorebuddy.databinding.ActivityLoginBinding
import org.setu.rugbyscorebuddy.main.MainApp

class LoginActivity : AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: ActivityLoginBinding
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        inputEmail = findViewById<TextInputEditText>(R.id.inputEmail)
        inputPassword = findViewById<TextInputEditText>(R.id.inputPassword)

        binding.btnLogin.setOnClickListener {
            validateInputs()
        }

        // Go to signup screen
        val goToSignup = findViewById<LinearLayout>(R.id.go_signup_screen)

        goToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun LoginActivity.validateInputs() {
        val email = inputEmail?.text?.toString() ?: ""
        val password = inputPassword?.text?.toString() ?: ""

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(inputEmail, "Please enter a valid email address")
        } else if (password.isEmpty() || password.length < 7) {
            showError(inputPassword, "Password must be at least 7 characters long")
        } else {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun LoginActivity.showError(
        input: TextInputEditText,
        string: String
    ) {
        input.setError(string)
        input.requestFocus()
    }
}