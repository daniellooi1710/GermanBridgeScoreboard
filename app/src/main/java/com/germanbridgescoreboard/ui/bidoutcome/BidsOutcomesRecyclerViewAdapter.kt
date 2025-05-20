package com.germanbridgescoreboard.ui.bidoutcome

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.databinding.FragmentBidWinBinding

class BidsOutcomesRecyclerViewAdapter(private val numPlayers: Int,
                                      private val names: Array<String>,
                                      private val gameProcess : MainViewModel.GAMEPROCESS,
                                      private val currentRoundBids : Array<Int>,
                                      private val currentRound: MutableLiveData<Int>)
    : RecyclerView.Adapter<BidsOutcomesRecyclerViewAdapter.ViewHolder>() {

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
            inBid.setOnValueChangedListener({ picker, oldVal, newVal -> bids[pos] = inBid.value })

            inWin.tag = "w${pos}"
            inWin.setOnValueChangedListener({ picker, oldVal, newVal -> wins[pos] = inWin.value })
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
            bids.add(0)
            wins.add(0)
        }
        else{
            holder.inBid.value = bids[position]
            holder.inWin.value = wins[position]
        }
        when (gameProcess) {
            MainViewModel.GAMEPROCESS.INIT, MainViewModel.GAMEPROCESS.ENDED -> {
                holder.inBid.isEnabled = false
                holder.inWin.isEnabled = false
            }
            MainViewModel.GAMEPROCESS.BIDDING -> {
                holder.inBid.isEnabled = true
                holder.inBid.selectedTextColor = Color.BLACK
                holder.inWin.isEnabled = false
                holder.inWin.selectedTextColor = Color.GRAY
                holder.inBid.maxValue = currentRound.value!!
                holder.inWin.maxValue = currentRound.value!!
            }
            MainViewModel.GAMEPROCESS.PLAYING -> {
                holder.inBid.value = currentRoundBids[position]
                holder.inBid.isEnabled = false
                holder.inBid.selectedTextColor = Color.GRAY
                holder.inWin.isEnabled = true
                holder.inWin.selectedTextColor = Color.BLACK
                holder.inBid.maxValue = currentRound.value!!
                holder.inWin.maxValue = currentRound.value!!
            }
        }
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