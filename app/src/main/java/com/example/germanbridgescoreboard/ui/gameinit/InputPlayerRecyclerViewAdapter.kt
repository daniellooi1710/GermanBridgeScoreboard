package com.example.germanbridgescoreboard.ui.gameinit

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.Game
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentInputBinding
import com.google.android.material.textfield.TextInputEditText


class InputPlayerRecyclerViewAdapter(val num: Int) : RecyclerView.Adapter<InputPlayerRecyclerViewAdapter.ViewHolder>() {
    var strArray = Array<String>(num){""}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.append(holder.bindingAdapterPosition)
        holder.myTextInputEditText.tag = holder.bindingAdapterPosition
    }

    override fun getItemCount(): Int {
        return num
    }

    inner class ViewHolder(binding: FragmentInputBinding) : RecyclerView.ViewHolder(binding.root) {
        var myTextView : TextView
        var myTextInputEditText : TextInputEditText

        init {
            myTextView = binding.playerNum
            myTextInputEditText = binding.inputPlayerName
            super.itemView
            myTextInputEditText.addTextChangedListener {
                object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        Log.d("debug",p0.toString())
                        if (myTextInputEditText.tag != null) {
                            strArray[bindingAdapterPosition] = p0.toString()
                        }
                    }
                    override fun afterTextChanged(text: Editable?) {
                        //Log.d("debug",text.toString())
                        //if(myTextInputEditText.getTag() != null){
                        //    strArray[myTextInputEditText.getTag().toString().toInt()] = myTextInputEditText.text.toString()
                        //}
                    }
                }
            }
        }

        fun append(position: Int){
            val text = "${myTextView.text} ${position + 1}"
            myTextView.text = text
        }

        fun bindName(){
            myTextInputEditText.addTextChangedListener {object:  TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(myTextInputEditText.tag != null){
                        strArray[myTextInputEditText.tag.toString().toInt()] = myTextInputEditText.text.toString()
                    }
                }

                override fun afterTextChanged(text: Editable?) {
                    //Log.d("debug",text.toString())
                    //if(myTextInputEditText.getTag() != null){
                    //    strArray[myTextInputEditText.getTag().toString().toInt()] = myTextInputEditText.text.toString()
                    //}
                }

            }
            }
        }
    }
    interface Callbacks {
        fun handleUserData(data: Array<String>)
    }
}