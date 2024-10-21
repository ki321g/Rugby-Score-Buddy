package org.setu.rugbyscorebuddy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.setu.rugbyscorebuddy.databinding.CardPlacemarkBinding
import org.setu.rugbyscorebuddy.models.RugbyScoreModel

interface RugbyScoreListener {
    fun onRugbyScoreClick(rugbygame: RugbyScoreModel)
}

class RugbyScoreAdapter constructor(private var rugbygames: List<RugbyScoreModel>,
                                    private val listener : RugbyScoreListener) :
    RecyclerView.Adapter<RugbyScoreAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val rugbygame = rugbygames[holder.adapterPosition]
        holder.bind(rugbygame, listener)
    }

    override fun getItemCount(): Int = rugbygames.size

    class MainHolder(private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rugbygame: RugbyScoreModel, listener: RugbyScoreListener) {
            binding.rugbyscoreHomeTeam.text = rugbygame.homeTeam
            binding.rugbyscoreAwayTeam.text = rugbygame.awayTeam
            binding.root.setOnClickListener { listener.onRugbyScoreClick(rugbygame) }
        }
    }
}