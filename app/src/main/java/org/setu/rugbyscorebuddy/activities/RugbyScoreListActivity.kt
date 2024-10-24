package org.setu.rugbyscorebuddy.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyScoreListBinding
import org.setu.rugbyscorebuddy.main.MainApp
import org.setu.rugbyscorebuddy.R
import org.setu.rugbyscorebuddy.adapters.RugbyScoreAdapter
import org.setu.rugbyscorebuddy.adapters.RugbyScoreListener
import org.setu.rugbyscorebuddy.models.RugbyScoreModel

class RugbyScoreListActivity : AppCompatActivity(), RugbyScoreListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityRugbyScoreListBinding
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRugbyScoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

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
}