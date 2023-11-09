package com.example.germanbridgescoreboard.ui.gameinit

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
        Log.d("debug bind", "${holder.bindingAdapterPosition}")
        holder.bindName(holder.bindingAdapterPosition)
        //holder.myTextInputEditText.tag = holder.bindingAdapterPosition
    }

    override fun getItemCount(): Int {
        return num
    }

    inner class ViewHolder(binding: FragmentInputBinding) : RecyclerView.ViewHolder(binding.root) {
        var myTextView = binding.playerNum
        var myTextInputLayout = binding.inputPlayerNameField
        var myTextInputEditText = binding.inputPlayerName

        init {
            /*super.itemView

            binding.inputPlayerName.addTextChangedListener(
                fun(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                },
                fun (p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d("debug",p0.toString())
                    strArray[bindingAdapterPosition] = p0.toString()
                },
                fun (text: Editable?) {

                }
            )
            Log.d("debug class", "$bindingAdapterPosition")*/
        }

        fun append(position: Int){
            val text = "${myTextView.text} ${position + 1}"
            myTextView.text = text
        }

        fun bindName(position: Int){
            myTextInputEditText.addTextChangedListener(
                fun(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                },
                fun (p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d("debug",p0.toString())
                    strArray[position] = p0.toString()
                    Log.d("debug array", strArray[position])
                },
                fun (text: Editable?) {
                    strArray[position] = text.toString()
                    Log.d("debug after", strArray[position])
                }
            )
            Log.d("debug class", "$position")
        }
    }
    interface Callbacks {
        fun handleUserData(data: Array<String>)
    }

    fun obtainName(): ArrayList<String>{
        return strArray
    }
}