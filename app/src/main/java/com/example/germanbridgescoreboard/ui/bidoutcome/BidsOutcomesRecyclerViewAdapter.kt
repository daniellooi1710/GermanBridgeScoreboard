package com.example.germanbridgescoreboard.ui.bidoutcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.databinding.FragmentBidWinBinding
import com.example.germanbridgescoreboard.databinding.FragmentInputBinding

class BidsOutcomesRecyclerViewAdapter(val numPlayers: Int, val names: Array<String>): RecyclerView.Adapter<BidsOutcomesRecyclerViewAdapter.ViewHolder>() {

    companion object{
        var bids = ArrayList<Int>()
        var wins = ArrayList<Int>()
    }

    inner class ViewHolder(binding: FragmentBidWinBinding) : RecyclerView.ViewHolder(binding.root){
        var tv = binding.playerName
        var inBid = binding.inputBid
        var inWin = binding.inputWin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentBidWinBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bids.add(0)
        wins.add(0)
    }

    override fun getItemCount(): Int {
        return numPlayers
    }


}