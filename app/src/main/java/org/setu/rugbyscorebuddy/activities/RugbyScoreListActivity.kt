package org.setu.rugbyscorebuddy.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyScoreListBinding
import org.setu.rugbyscorebuddy.main.MainApp
import org.setu.rugbyscorebuddy.R
import org.setu.rugbyscorebuddy.adapters.RugbyScoreAdapter
import org.setu.rugbyscorebuddy.adapters.RugbyScoreListener
import org.setu.rugbyscorebuddy.helpers.SessionManager
import org.setu.rugbyscorebuddy.models.RugbyScoreModel

class RugbyScoreListActivity : AppCompatActivity(), RugbyScoreListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityRugbyScoreListBinding
    private lateinit var sessionManager: SessionManager
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRugbyScoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        // Initialize SessionManager
        sessionManager = SessionManager(this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RugbyScoreAdapter(app.rugbygames.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, RugbyScoreActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, RugbyScoreMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
            R.id.item_logout -> {
                sessionManager.logout()
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                val launcherIntent = Intent(this, LoginActivity::class.java)
                startActivity(launcherIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.rugbygames.findAll().size)
            }
        }

    override fun onRugbyScoreClick(rugbygame: RugbyScoreModel, pos : Int) {
        val launcherIntent = Intent(this, RugbyScoreActivity::class.java)
        launcherIntent.putExtra("rugbygame_edit", rugbygame)
        position = pos
        getClickResult.launch(launcherIntent)
    }
    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.rugbygames.findAll().size)
            } else // Deleting
                if (it.resultCode == 99)
                    (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }
}