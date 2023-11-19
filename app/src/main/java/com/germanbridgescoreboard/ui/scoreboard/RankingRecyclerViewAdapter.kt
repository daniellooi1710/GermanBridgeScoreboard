package com.germanbridgescoreboard.ui.scoreboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.germanbridgescoreboard.R

class RankingRecyclerViewAdapter(val context: Context, val playerCount: Int, val players: Array<String>, val scores: Array<Int>): RecyclerView.Adapter<RankingRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById<TextView>(R.id.textView8)
        var score = itemView.findViewById<TextView>(R.id.textView9)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.dialog_ranking_list_element, parent, false))
    }

    override fun getItemCount(): Int {
        return playerCount
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = players[position]
        holder.score.text = scores[position].toString()
    }
}