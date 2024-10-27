package org.setu.rugbyscorebuddy.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import android.view.animation.OvershootInterpolator
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.setu.rugbyscorebuddy.R
import org.setu.rugbyscorebuddy.models.RugbyScoreViewModel

class SplashScreenActivity : AppCompatActivity() {
    //lateinit var app: MainApp
    private val viewModel by viewModels<RugbyScoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Working with splashScreen
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
            // Set Exit of the splash screen animation
            setOnExitAnimationListener { screen ->
                // Zoom Out the X Value
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.8f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                // Zoom Out the Y Value
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.8f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide() // Hide the ActionBar
        // Change ActionBar color
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)))

        val goToRugbyscore = findViewById<Button>(R.id.loginButton)

        goToRugbyscore.setOnClickListener {
            // Navigate to LoginActivity
            val intent = Intent(this, RugbyScoreListActivity::class.java)
            startActivity(intent)
            finish()
        }

//        signupButton.setOnClickListener {
//            // Navigate to SignupActivity
//            startActivity(Intent(this, SignupActivity::class.java))
//        }

    }
}