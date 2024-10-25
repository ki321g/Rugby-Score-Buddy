package org.setu.rugbyscorebuddy.activities

import android.R.id.edit
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyscoreBinding
import org.setu.rugbyscorebuddy.main.MainApp
import org.setu.rugbyscorebuddy.models.RugbyScoreModel
import org.setu.rugbyscorebuddy.R
import org.setu.rugbyscorebuddy.helpers.HorizontalNumberPicker
import org.setu.rugbyscorebuddy.helpers.showImagePicker
import timber.log.Timber.i

class RugbyScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRugbyscoreBinding
    var rugbygame = RugbyScoreModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRugbyscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("RugbyScoreBuddy Activity Started...")

        if (intent.hasExtra("rugbygame_edit")) {
            edit = true
            rugbygame = intent.extras?.getParcelable("rugbygame_edit")!!
            // Home Team
            binding.rugbyscoreHomeTeam.setText(rugbygame.homeTeam)
            val npHomeTries: HorizontalNumberPicker = findViewById(R.id.noHomeTries)
            npHomeTries.setValue(rugbygame.homeTeamTries)
            val npHomeConversions: HorizontalNumberPicker = findViewById(R.id.noHomeConversions)
            npHomeConversions.setValue(rugbygame.homeTeamConversions)
            val npHomePenalties: HorizontalNumberPicker = findViewById(R.id.noHomePenalties)
            npHomePenalties.setValue(rugbygame.homeTeamPenalties)

           // Away Team
            binding.rugbyscoreAwayTeam.setText(rugbygame.awayTeam)
            val noAwayTries: HorizontalNumberPicker = findViewById(R.id.noAwayTries)
            noAwayTries.setValue(rugbygame.awayTeamTries)
            val noAwayConversions: HorizontalNumberPicker = findViewById(R.id.noAwayConversions)
            noAwayConversions.setValue(rugbygame.awayTeamConversions)
            val noAwayPenalties: HorizontalNumberPicker = findViewById(R.id.noAwayPenalties)
            noAwayPenalties.setValue(rugbygame.awayTeamPenalties)

            // Button
            binding.btnAdd.setText(R.string.save_RugbyGame)

            // Set Scores
            binding.textHomeScore.setText(calculateScore(rugbygame.homeTeamTries, rugbygame.homeTeamConversions, rugbygame.homeTeamPenalties).toString())
            binding.textAwayScore.setText(calculateScore(rugbygame.awayTeamTries, rugbygame.awayTeamConversions, rugbygame.awayTeamPenalties).toString())

            // Load Game image
            Picasso.get()
                .load(rugbygame.image)
                .into(binding.rugbygameImage)

            if (rugbygame.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_rugbygame_image)
            }


        }

        binding.chooseImage.setOnClickListener {
            i("Select Game Image")
            // showImagePicker(imageIntentLauncher,this)
            val request = PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
            imageIntentLauncher.launch(request)
        }
        registerImagePickerCallback()

        binding.btnAdd.setOnClickListener {
            // Home Team
            rugbygame.homeTeam = binding.rugbyscoreHomeTeam.text.toString()
            val nohomeTries: HorizontalNumberPicker = findViewById(R.id.noHomeTries)
            rugbygame.homeTeamTries = nohomeTries.getValue()
            val npHomeConversions: HorizontalNumberPicker = findViewById(R.id.noHomeConversions)
            rugbygame.homeTeamConversions = npHomeConversions.getValue()
            val npHomePenalties: HorizontalNumberPicker = findViewById(R.id.noHomePenalties)
            rugbygame.homeTeamPenalties = npHomePenalties.getValue()

            // Away Team
            rugbygame.awayTeam = binding.rugbyscoreAwayTeam.text.toString()
            val noAwayTries: HorizontalNumberPicker = findViewById(R.id.noAwayTries)
            rugbygame.awayTeamTries = noAwayTries.getValue()
            val noAwayConversions: HorizontalNumberPicker = findViewById(R.id.noAwayConversions)
            rugbygame.awayTeamConversions = noAwayConversions.getValue()
            val noAwayPenalties: HorizontalNumberPicker = findViewById(R.id.noAwayPenalties)
            rugbygame.awayTeamPenalties = noAwayPenalties.getValue()

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

        val homeScore = findViewById<TextView>(R.id.textHomeScore)
        val awayScore = findViewById<TextView>(R.id.textAwayScore)

        // Set up each HorizontalNumberPicker and update the corresponding value when it changes
        val npHomeTries: HorizontalNumberPicker = findViewById(R.id.noHomeTries)
        val npHomeConversions: HorizontalNumberPicker = findViewById(R.id.noHomeConversions)
        val npHomePenalties: HorizontalNumberPicker = findViewById(R.id.noHomePenalties)
        val npAwayTries: HorizontalNumberPicker = findViewById(R.id.noAwayTries)
        val npAwayConversions: HorizontalNumberPicker = findViewById(R.id.noAwayConversions)
        val npAwayPenalties: HorizontalNumberPicker = findViewById(R.id.noAwayPenalties)

        // Set up the listeners for each HorizontalNumberPicker
        // Listener for Home Team Tries
        setupNumberPicker(npHomeTries) { newValue ->
            setScore(npHomeTries.getValue(), npHomeConversions.getValue(), npHomePenalties.getValue(), homeScore )
        }

        // Listener for Home Team Conversions
        setupNumberPicker(npHomeConversions) { newValue ->
            setScore(npHomeTries.getValue(), npHomeConversions.getValue(), npHomePenalties.getValue(), homeScore )
        }

        // Listener for Home Team Penalties
        setupNumberPicker(npHomePenalties) { newValue ->
            setScore(npHomeTries.getValue(), npHomeConversions.getValue(), npHomePenalties.getValue(), homeScore )
        }

        // Listener for Away Team Tries
        setupNumberPicker(npAwayTries) { newValue ->
            setScore(npAwayTries.getValue(), npAwayConversions.getValue(), npAwayPenalties.getValue(), awayScore  )
        }

        // Listener for Away Team Conversions
        setupNumberPicker(npAwayConversions) { newValue ->
            setScore(npAwayTries.getValue(), npAwayConversions.getValue(), npAwayPenalties.getValue(), awayScore  )
        }

        // Listener for Away Team Penalties
        setupNumberPicker(npAwayPenalties) { newValue ->
            setScore(npAwayTries.getValue(), npAwayConversions.getValue(), npAwayPenalties.getValue(), awayScore  )
        }
    }

    // Function to set the homeScore & awayScore based on the number of tries, conversions, and penalties
    fun setScore(teamTries: Int,
        teamConversions: Int,
        teamPenalties: Int,
        teamScore: TextView) {

        val totalScore = (5 * teamTries) + (2 * teamConversions) + (3 * teamPenalties)
        teamScore.setText(totalScore.toString())
    }

    // Function to calculate the score based on the number of tries, conversions, and penalties
    fun calculateScore(tries: Int, conversions: Int, penalties: Int): Int {
        return (5 * tries) + (2 * conversions) + (3 * penalties)
    }

    // Helper function to set up the HorizontalNumberPicker listener
    fun setupNumberPicker(picker: HorizontalNumberPicker, onValueChanged: (Int) -> Unit) {
        picker.setOnValueChangedListener { newValue ->
            onValueChanged(newValue) // Call the onValueChanged callback
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_rugby_score, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        i("onOptionsItemSelected Started...")
        when (item.itemId) {
            R.id.item_delete -> {
                i("onOptionsItemSelected: item_delete")
                setResult(99)
                app.rugbygames.delete(rugbygame)
                finish()
            }
            R.id.item_cancel -> {
                i("onOptionsItemSelected: item_cancel")
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                rugbygame.image = it // The returned Uri
                i("IMG :: ${rugbygame.image}")
                Picasso.get()
                    .load(rugbygame.image)
                    .into(binding.rugbygameImage)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}