package com.example.germanbridgescoreboard.ui.gameinit

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.databinding.FragmentInputBinding


class InputPlayerRecyclerViewAdapter(val num: Int) : RecyclerView.Adapter<InputPlayerRecyclerViewAdapter.ViewHolder>() {
    companion object{
        var strArray : ArrayList<String> = ArrayList<String>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        strArray.add("")
        holder.append(holder.bindingAdapterPosition)
        holder.bindName(holder.bindingAdapterPosition)
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
            myTextInputEditText.addTextChangedListener(
                fun(_: CharSequence?, _: Int, _: Int, _: Int) {
                    // Implementation was unnecessary
                },
                fun (p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    strArray[position] = p0.toString()
                },
                fun (_: Editable?) {
                    // Implementation was unnecessary
                }
            )
        }
    }

    fun obtainName(): ArrayList<String>{
        return strArray
    }

    fun resetArray(){
        strArray.clear()
    }
}