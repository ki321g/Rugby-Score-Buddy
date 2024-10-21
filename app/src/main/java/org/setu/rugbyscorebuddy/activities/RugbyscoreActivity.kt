package org.setu.rugbyscorebuddy.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyscoreBinding
import org.setu.rugbyscorebuddy.models.RugbyScoreModel
import timber.log.Timber
import timber.log.Timber.i

class RugbyscoreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRugbyscoreBinding
    var rugbyscore = RugbyScoreModel()
    val rugbyscores = ArrayList<RugbyScoreModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRugbyscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Rugby Score Buddy Activity started...")

        binding.btnAdd.setOnClickListener() {
            rugbyscore.homeTeam = binding.rugbyscoreHomeTeam.text.toString()
            rugbyscore.awayTeam = binding.rugbyscoreAwayTeam.text.toString()
            i("homeTeam: $rugbyscore.homeTeam")
            i("awayTeam: $rugbyscore.awayTeam")

            if (rugbyscore.homeTeam.isNotEmpty() && rugbyscore.awayTeam.isNotEmpty()) {
                rugbyscores.add(rugbyscore.copy())
                i("addButton Pressed: $rugbyscore")
                for (i in rugbyscores.indices)
                { i("RugbyScore[$i]:${this.rugbyscores[i]}") }
            }
            else {
                if (rugbyscore.homeTeam.isEmpty() && rugbyscore.awayTeam.isEmpty()) {
                    Snackbar
                        .make(it,"Please Enter Team Name's", Snackbar.LENGTH_LONG)
                        .show()
                }
                else if (rugbyscore.homeTeam.isEmpty()) {
                    Snackbar
                        .make(it,"Please Enter a Home Team Name", Snackbar.LENGTH_LONG)
                        .show()
                }
                else if (rugbyscore.awayTeam.isEmpty()) {
                    Snackbar
                        .make(it,"Please Enter a Away Team Name", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}