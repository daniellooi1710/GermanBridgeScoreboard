package com.example.germanbridgescoreboard.ui.gameinit

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.germanbridgescoreboard.Game

import com.example.germanbridgescoreboard.placeholder.PlaceholderContent.PlaceholderItem
import com.example.germanbridgescoreboard.databinding.FragmentInputBinding

class InputPlayerRecyclerViewAdapter(var game: Game) : RecyclerView.Adapter<InputPlayerRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.append(position)
    }

    override fun getItemCount(): Int = game.playerCount

    inner class ViewHolder(binding: FragmentInputBinding) : RecyclerView.ViewHolder(binding.root) {
        var myTextView = binding.playerNum
        var myTextInputLayout = binding.inputPlayerNameField
        var myTextInputEditText = binding.inputPlayerName

        fun append(position: Int){
            val text = "${myTextView.text} ${position + 1}"
            myTextView.text = text
        }

        fun getName(position: Int){
            val name = myTextInputEditText.text.toString()
            game.players[position].name = name
        }
    }

}