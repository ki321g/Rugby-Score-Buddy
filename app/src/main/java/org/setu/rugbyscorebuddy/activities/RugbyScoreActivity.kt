package org.setu.rugbyscorebuddy.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyscoreBinding
import org.setu.rugbyscorebuddy.main.MainApp
import org.setu.rugbyscorebuddy.models.RugbyScoreModel
import org.setu.rugbyscorebuddy.R
import timber.log.Timber.i

class RugbyScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRugbyscoreBinding
    var rugbygame = RugbyScoreModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false

        binding = ActivityRugbyscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("RugbyScoreBuddy Activity Started...")

        if (intent.hasExtra("rugbygame_edit")) {
            edit = true
            rugbygame = intent.extras?.getParcelable("rugbygame_edit")!!
            binding.rugbyscoreHomeTeam.setText(rugbygame.homeTeam)
            binding.rugbyscoreAwayTeam.setText(rugbygame.awayTeam)
            binding.btnAdd.setText(R.string.save_RugbyGame)
        }

        binding.btnAdd.setOnClickListener {
            rugbygame.homeTeam = binding.rugbyscoreHomeTeam.text.toString()
            rugbygame.awayTeam = binding.rugbyscoreAwayTeam.text.toString()
            //i("homeTeam: $rugbygame.homeTeam")
            //i("awayTeam: $rugbygame.awayTeam")

            if (rugbygame.homeTeam.isNotEmpty() && rugbygame.awayTeam.isNotEmpty()) {
                if (edit) {
                    i("saveButton Pressed: $rugbygame")
                    app.rugbygames.update(rugbygame.copy())
                } else {
                    i("addButton Pressed: $rugbygame")
                    app.rugbygames.create(rugbygame.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                if (rugbygame.homeTeam.isEmpty() && rugbygame.awayTeam.isEmpty()) {
                    Snackbar
                        .make(it,R.string.enter_RugbyTeamNames, Snackbar.LENGTH_LONG)
                        .show()
                }
                else if (rugbygame.homeTeam.isEmpty()) {
                    Snackbar
                        .make(it,R.string.enter_RugbyHomeTeamName, Snackbar.LENGTH_LONG)
                        .show()
                }
                else if (rugbygame.awayTeam.isEmpty()) {
                    Snackbar
                        .make(it,R.string.enter_RugbyAwayTeamName, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_rugby_score, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}