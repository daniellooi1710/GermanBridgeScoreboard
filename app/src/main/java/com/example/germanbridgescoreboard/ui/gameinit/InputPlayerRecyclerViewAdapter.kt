package com.example.germanbridgescoreboard.ui.gameinit

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.Game
import com.example.germanbridgescoreboard.databinding.FragmentInputBinding


class InputPlayerRecyclerViewAdapter(gameDetail: Game) : RecyclerView.Adapter<InputPlayerRecyclerViewAdapter.ViewHolder>() {
    val num = gameDetail.playerCount
    var strArray = Array<String>(num){""}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.append(position)
        holder.bindName(position)
    }

    override fun getItemCount(): Int {
        return num
    }


    inner class ViewHolder(binding: FragmentInputBinding) : RecyclerView.ViewHolder(binding.root) {
        var myTextView = binding.playerNum
        var myTextInputLayout = binding.inputPlayerNameField
        var myTextInputEditText = binding.inputPlayerName

        fun append(position: Int){
            val text = "${myTextView.text} ${position + 1}"
            myTextView.text = text
        }

        fun bindName(position: Int){
            myTextInputEditText.addTextChangedListener {object:  TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    strArray[position] = p0.toString()
                }

                override fun afterTextChanged(text: Editable?) {

                }

            }
            }
        }
    }
    interface Callbacks {
        fun handleUserData(data: Array<String>)
    }
}