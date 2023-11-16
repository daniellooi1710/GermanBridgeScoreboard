package com.example.germanbridgescoreboard.ui.bidoutcome

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentBidWinBinding

class BidsOutcomesRecyclerViewAdapter(private val numPlayers: Int, private val names: Array<String>, private val gameProcess : MainViewModel.GAMEPROCESS): RecyclerView.Adapter<BidsOutcomesRecyclerViewAdapter.ViewHolder>() {

    companion object{
        var bids = ArrayList<Int>()
        var wins = ArrayList<Int>()
    }

    inner class ViewHolder(binding: FragmentBidWinBinding) : RecyclerView.ViewHolder(binding.root) {
        var tv = binding.playerName
        var inBid = binding.inputBid
        var inWin = binding.inputWin

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
        holder.bind(position)
        if(bids.size < numPlayers){
            bids.add(-1)
            wins.add(-1)
        }
        else{
            if(bids[position] != -1) holder.inBid.setText("" + bids[position])
            if(wins[position] != -1) holder.inWin.setText("" + wins[position])
        }
        when (gameProcess) {
            MainViewModel.GAMEPROCESS.BIDDING -> {
                holder.inBid.isEnabled = true
                holder.inWin.isEnabled = false
            }

            MainViewModel.GAMEPROCESS.PLAYING -> {
                holder.inBid.isEnabled = false
                holder.inWin.isEnabled = true
            }
        }
    }

    override fun getItemCount(): Int {
        return numPlayers
    }

    fun resetArrays(){
        for(i in 0 until bids.size){
            bids[i] = -1
            wins[i] = -1
        }
    }

    fun obtainBids(): ArrayList<Int> {
        return bids
    }

    fun obtainWins(): ArrayList<Int> {
        return wins
    }

}