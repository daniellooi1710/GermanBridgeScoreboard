package com.example.germanbridgescoreboard.ui.bidoutcome

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.databinding.FragmentBidWinBinding

class BidsOutcomesRecyclerViewAdapter(private val numPlayers: Int, private val names: Array<String>): RecyclerView.Adapter<BidsOutcomesRecyclerViewAdapter.ViewHolder>() {

    companion object{
        var bids = ArrayList<Int>()
        var wins = ArrayList<Int>()
    }

    inner class ViewHolder(binding: FragmentBidWinBinding) : RecyclerView.ViewHolder(binding.root) {
        var tv = binding.playerName
        private var inBid = binding.inputBid
        private var inWin = binding.inputWin

        fun bind(pos : Int){
            inBid.tag = "b${pos}"
            inBid.addTextChangedListener(
                fun(_: CharSequence?, _: Int, _: Int, _: Int) {
                    // Implementation was unnecessary
                },
                fun (_: CharSequence?, _: Int, _: Int, _: Int) {
                    // Implementation was unnecessary
                },
                fun (text: Editable?) {
                    if(text.toString() == "") bids[pos] = 0 else bids[pos] = text.toString().toInt()
                }
            )
            inWin.tag = "w${pos}"
            inWin.addTextChangedListener(
                fun(_: CharSequence?, _: Int, _: Int, _: Int) {
                    // Implementation was unnecessary
                                                              },
                fun (_: CharSequence?, _: Int, _: Int, _: Int) {
                    // Implementation was unnecessary
                },
                fun (text: Editable?) {
                    if(text.toString() == "") wins[pos] = 0 else wins[pos] = text.toString().toInt()
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentBidWinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = names[position]
        bids.add(0)
        wins.add(0)
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return numPlayers
    }

    fun resetArrays(){
        for(i in 0 until bids.size){
            bids[i] = 0
            wins[i] = 0
        }
    }

    fun obtainBids(): ArrayList<Int> {
        return bids
    }

    fun obtainWins(): ArrayList<Int> {
        return wins
    }

}