package org.setu.rugbyscorebuddy.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
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
        var calcHomeScore = 0
        var calcAwayScore = 0
        var calcHomeTries = 0
        var calcAwayTries = 0
        var calcHomeConversions = 0
        var calcAwayConversions = 0
        var calcHomePenalties = 0


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
            binding.textHomeTries.setText(rugbygame.homeTeamTries.toString())
            binding.textHomeConversions.setText(rugbygame.homeTeamConversions.toString())
            binding.textHomePenalties.setText(rugbygame.homeTeamPenalties.toString())
            binding.rugbyscoreAwayTeam.setText(rugbygame.awayTeam)
            binding.textAwayTries.setText(rugbygame.awayTeamTries.toString())
            binding.textAwayConversions.setText(rugbygame.awayTeamConversions.toString())
            binding.textAwayPenalties.setText(rugbygame.awayTeamPenalties.toString())
            binding.btnAdd.setText(R.string.save_RugbyGame)
            binding.textHomeScore.setText(calculateScore(rugbygame.homeTeamTries, rugbygame.homeTeamConversions, rugbygame.homeTeamPenalties).toString())
            binding.textAwayScore.setText(calculateScore(rugbygame.awayTeamTries, rugbygame.awayTeamConversions, rugbygame.awayTeamPenalties).toString())
        }

        binding.btnAdd.setOnClickListener {
            rugbygame.homeTeam = binding.rugbyscoreHomeTeam.text.toString()
            rugbygame.homeTeamTries = binding.textHomeTries.text.toString().toIntOrNull() ?: 0
            rugbygame.homeTeamConversions = binding.textHomeConversions.text.toString().toIntOrNull() ?: 0
            rugbygame.homeTeamPenalties = binding.textHomePenalties.text.toString().toIntOrNull() ?: 0
            rugbygame.awayTeam = binding.rugbyscoreAwayTeam.text.toString()
            rugbygame.awayTeamTries = binding.textAwayTries.text.toString().toIntOrNull() ?: 0
            rugbygame.awayTeamConversions = binding.textAwayConversions.text.toString().toIntOrNull() ?: 0
            rugbygame.awayTeamPenalties = binding.textAwayPenalties.text.toString().toIntOrNull() ?: 0

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

        val homeTries = findViewById<EditText>(R.id.textHomeTries)
        val homeConversions = findViewById<EditText>(R.id.textHomeConversions)
        val homePenalties = findViewById<EditText>(R.id.textHomePenalties)
        val homeScore = findViewById<TextView>(R.id.textHomeScore)
        val awayScore = findViewById<TextView>(R.id.textAwayScore)
        val awayTries = findViewById<EditText>(R.id.textAwayTries)
        val awayConversions = findViewById<EditText>(R.id.textAwayConversions)
        val awayPenalties = findViewById<EditText>(R.id.textAwayPenalties)

        // Home team TextWatcher
        homeTries.addTextChangedListener(createScoreTextWatcher(
            homeTries, homeConversions, homePenalties, homeScore
        ))

        homeConversions.addTextChangedListener(createScoreTextWatcher(
            homeTries, homeConversions, homePenalties, homeScore
        ))

        homePenalties.addTextChangedListener(createScoreTextWatcher(
            homeTries, homeConversions, homePenalties, homeScore
        ))

// Away team TextWatcher
        awayTries.addTextChangedListener(createScoreTextWatcher(
            awayTries, awayConversions, awayPenalties, awayScore
        ))

        awayConversions.addTextChangedListener(createScoreTextWatcher(
            awayTries, awayConversions, awayPenalties, awayScore
        ))

        awayPenalties.addTextChangedListener(createScoreTextWatcher(
            awayTries, awayConversions, awayPenalties, awayScore
        ))


    }

    fun calculateScore(tries: Int, conversions: Int, penalties: Int): Int {
        return (5 * tries) + (2 * conversions) + (3 * penalties)
    }

    fun createScoreTextWatcher(
        teamTries: EditText,
        teamConversions: EditText,
        teamPenalties: EditText,
        teamScore: TextView
    ): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val tries = teamTries.text.toString().toIntOrNull() ?: 0
                val conversions = teamConversions.text.toString().toIntOrNull() ?: 0
                val penalties = teamPenalties.text.toString().toIntOrNull() ?: 0

                val totalScore = calculateScore(tries, conversions, penalties)
                teamScore.setText(totalScore.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
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