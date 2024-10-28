package org.setu.rugbyscorebuddy.activities

import android.R.id.input
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

import org.setu.rugbyscorebuddy.R
import org.setu.rugbyscorebuddy.databinding.ActivitySignUpBinding
import org.setu.rugbyscorebuddy.main.MainApp
import kotlin.toString

class SignUpActivity : AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputConfirmPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        app = application as MainApp
        enableEdgeToEdge()
        setContentView(binding.root)


        inputEmail = findViewById<TextInputEditText>(R.id.inputEmail)
        inputPassword = findViewById<TextInputEditText>(R.id.inputPassword)
        inputConfirmPassword = findViewById<TextInputEditText>(R.id.inputConfirmPassword)

        binding.btnSignUp.setOnClickListener() {
            validateInputs()
        }

        // Go back to login screen
        val goTologin = findViewById<LinearLayout>(R.id.go_login_screen)

        goTologin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun SignUpActivity.validateInputs() {
        val email = inputEmail?.text?.toString() ?: ""
        val password = inputPassword?.text?.toString() ?: ""
        val confirmPassword = inputConfirmPassword?.text?.toString() ?: ""

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(inputEmail, "Please enter a valid email address")
        } else if (password.isEmpty() || password.length < 7) {
            showError(inputPassword, "Password must be at least 7 characters long")
        } else if (confirmPassword.isEmpty() || confirmPassword != password) {
            showError(inputConfirmPassword, "Passwords do not match")
        } else {
            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun SignUpActivity.showError(
        input: TextInputEditText,
        string: String
    ) {
        input.setError(string)
        input.requestFocus()
    }
}



