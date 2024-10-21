package org.setu.rugbyscorebuddy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.rugbyscorebuddy.databinding.ActivityRugbyScoreListBinding
import org.setu.rugbyscorebuddy.databinding.CardPlacemarkBinding
import org.setu.rugbyscorebuddy.main.MainApp
import org.setu.rugbyscorebuddy.models.RugbyScoreModel
import org.setu.rugbyscorebuddy.R

class RugbyScoreListActivity : AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: ActivityRugbyScoreListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRugbyScoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RugbyScoreAdapter(app.rugbygames)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}


class RugbyScoreAdapter constructor(private var rugbygames: List<RugbyScoreModel>) :
    RecyclerView.Adapter<RugbyScoreAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val rugbygame = rugbygames[holder.adapterPosition]
        holder.bind(rugbygame)
    }

    override fun getItemCount(): Int = rugbygames.size

    class MainHolder(private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rugbygame: RugbyScoreModel) {
            binding.rugbyscoreHomeTeam.text = rugbygame.homeTeam
            binding.rugbyscoreAwayTeam.text = rugbygame.awayTeam
        }
    }
}