package org.setu.rugbyscorebuddy.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyscoreBinding
import org.setu.rugbyscorebuddy.main.MainApp
import org.setu.rugbyscorebuddy.models.RugbyScoreModel
import timber.log.Timber.i

class RugbyScoreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRugbyscoreBinding
    var rugbygame = RugbyScoreModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRugbyscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("RugbyScoreBuddy Activity Started...")

        binding.btnAdd.setOnClickListener {
            rugbygame.homeTeam = binding.rugbyscoreHomeTeam.text.toString()
            rugbygame.awayTeam = binding.rugbyscoreAwayTeam.text.toString()
            //i("homeTeam: $rugbygame.homeTeam")
            //i("awayTeam: $rugbygame.awayTeam")

            if (rugbygame.homeTeam.isNotEmpty() && rugbygame.awayTeam.isNotEmpty()) {
                app.rugbygames.add(rugbygame.copy())
                i("addButton Pressed: $rugbygame")
                for (i in app.rugbygames.indices)
                { i("RugbyScore[$i]:${this.app.rugbygames[i]}") }
            }
            else {
                if (rugbygame.homeTeam.isEmpty() && rugbygame.awayTeam.isEmpty()) {
                    Snackbar
                        .make(it,"Please Enter Team Name's", Snackbar.LENGTH_LONG)
                        .show()
                }
                else if (rugbygame.homeTeam.isEmpty()) {
                    Snackbar
                        .make(it,"Please Enter a Home Team Name", Snackbar.LENGTH_LONG)
                        .show()
                }
                else if (rugbygame.awayTeam.isEmpty()) {
                    Snackbar
                        .make(it,"Please Enter a Away Team Name", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}