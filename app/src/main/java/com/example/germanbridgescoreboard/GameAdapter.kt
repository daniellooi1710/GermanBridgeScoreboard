package com.example.germanbridgescoreboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class GameAdapter(var playerCount: Int) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textInputLayout: TextInputLayout

        init{
            textView = view.findViewById(R.id.player_num)
            textInputLayout = view.findViewById(R.id.input_player_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val playerInputView = inflater.inflate(R.layout.item_game_init, parent, false)
        return ViewHolder(playerInputView)
    }

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        val textView = holder.textView
        textView.append(" " + (position + 1))
    }

    override fun getItemCount(): Int {
        return playerCount
    }
}