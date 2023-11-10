package com.example.germanbridgescoreboard.ui.bidoutcome

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.databinding.FragmentBidWinBinding
import com.example.germanbridgescoreboard.databinding.FragmentInputBinding
import com.example.germanbridgescoreboard.ui.gameinit.InputPlayerRecyclerViewAdapter

class BidsOutcomesRecyclerViewAdapter(val numPlayers: Int, val names: Array<String>): RecyclerView.Adapter<BidsOutcomesRecyclerViewAdapter.ViewHolder>() {

    companion object{
        var bids = ArrayList<Int>()
        var wins = ArrayList<Int>()
    }

    inner class ViewHolder(binding: FragmentBidWinBinding) : RecyclerView.ViewHolder(binding.root){
        var tv = binding.playerName
        var inBid = binding.inputBid
        var inWin = binding.inputWin

        fun bind(pos : Int){
            inBid.addTextChangedListener(
                fun(_: CharSequence?, _: Int, _: Int, _: Int) {

                },
                fun (p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    bids[pos] = p0.toString().toInt()
                },
                fun (_: Editable?) {
                }
            )
            inWin.addTextChangedListener(
                fun(_: CharSequence?, _: Int, _: Int, _: Int) {

                },
                fun (p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    wins[pos] = p0.toString().toInt()
                },
                fun (_: Editable?) {
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentBidWinBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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


}