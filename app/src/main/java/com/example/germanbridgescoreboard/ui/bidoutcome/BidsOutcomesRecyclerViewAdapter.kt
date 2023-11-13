package com.example.germanbridgescoreboard.ui.bidoutcome

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentBidWinBinding

class BidsOutcomesRecyclerViewAdapter(val numPlayers: Int, val names: Array<String>): RecyclerView.Adapter<BidsOutcomesRecyclerViewAdapter.ViewHolder>() {

    companion object{
        var bids = ArrayList<Int>()
        var wins = ArrayList<Int>()
        var gameProcess = false
    }

    inner class ViewHolder(binding: FragmentBidWinBinding) : RecyclerView.ViewHolder(binding.root) {
        var tv = binding.playerName
        var inBid = binding.inputBid
        var inWin = binding.inputWin

        fun bind(pos : Int){
            inBid.tag = "b${pos}"
            inBid.addOnAttachStateChangeListener(
                object : View.OnAttachStateChangeListener {
                    override fun onViewAttachedToWindow(v: View) {
                        if(!gameProcess) inBid.isEnabled = true else inBid.isEnabled = false
                    }

                    override fun onViewDetachedFromWindow(v: View) {
                        inBid.clearFocus()
                    }
                }
            )
            inBid.addTextChangedListener(
                fun(_: CharSequence?, _: Int, _: Int, _: Int) {
                    // Implementation was unnecessary
                },
                fun (p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d("adapter", inBid.tag.toString())
                    if(p0.toString() == "") bids[pos] = 0 else bids[pos] = p0.toString().toInt()
                },
                fun (_: Editable?) {
                    // Implementation was unnecessary
                }
            )
            inWin.tag = "w${pos}"
            inWin.addOnAttachStateChangeListener(
                object : View.OnAttachStateChangeListener {
                    override fun onViewAttachedToWindow(v: View) {
                        if(!gameProcess) inWin.isEnabled = false else inWin.isEnabled = true
                    }

                    override fun onViewDetachedFromWindow(v: View) {
                        inBid.clearFocus()
                    }
                }
            )
            inWin.addTextChangedListener(
                fun(_: CharSequence?, _: Int, _: Int, _: Int) {
                    // Implementation was unnecessary
                                                              },
                fun (p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(p0.toString() == "") bids[pos] = 0 else wins[pos] = p0.toString().toInt()
                },
                fun (_: Editable?) {
                    // Implementation was unnecessary
                }
            )

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(FragmentBidWinBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return vh
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
        bids.clear()
        wins.clear()
    }

    fun obtainBids(): ArrayList<Int> {
        return bids
    }

    fun obtainWins(): ArrayList<Int> {
        return wins
    }

}