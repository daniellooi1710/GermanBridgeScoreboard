package com.example.germanbridgescoreboard.ui.bidoutcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentBidsOutcomesBinding
import com.example.germanbridgescoreboard.ui.bidoutcome.BidsOutcomesRecyclerViewAdapter
import com.google.android.material.textfield.TextInputEditText
import java.util.Objects

class BidsOutcomesFragment : Fragment() {
    private val viewmodel: MainViewModel by activityViewModels()
    private var _binding: FragmentBidsOutcomesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidsOutcomesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lateinit var adapter : BidsOutcomesRecyclerViewAdapter
        if(viewmodel.playerCount == 0) {
            binding.buttonNext.visibility = View.GONE
        }

        if(viewmodel.playerCount > 0) {
            binding.buttonNext.visibility = View.VISIBLE
            binding.textViewGameProcess.text = getString(R.string.bidding)
            val nameArray = viewmodel.players
            val view2 = root.findViewById<RecyclerView>(R.id.bid_win_table)
            if(view2 is RecyclerView){
                with(view2) {
                    this.setItemViewCacheSize(12)
                    this.isNestedScrollingEnabled = false
                    layoutManager = LinearLayoutManager(context)
                    this.adapter = BidsOutcomesRecyclerViewAdapter(viewmodel.playerCount, nameArray)
                    adapter = this.adapter as BidsOutcomesRecyclerViewAdapter
                }
            }
        }

        viewmodel.currentRound.observe(viewLifecycleOwner, Observer {
            binding.textViewRoundNumber.text = it.toString()
        })

        binding.buttonNext.setOnClickListener{
            var error = false
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            if(adapter.obtainBids().sum() == viewmodel.currentRound.value){
                error = true
                builder
                    .setMessage("Total number of bids cannot be equal to the number of current round.")
                    .setTitle("Rule Violation")
                    .setNeutralButton("Okay") { dialog, which -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            else{
                for(i in 0 until viewmodel.playerCount){
                    if(adapter.obtainBids()[i] > viewmodel.currentRound.value!!){
                        error = true
                        builder
                            .setMessage("Player's number of bids cannot be more than the number of current round.")
                            .setTitle("Rule Violation")
                            .setPositiveButton("Okay") { dialog, which -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                }
            }
            if(!error){
                for(i in 0 until viewmodel.playerCount){
                    viewmodel.playerBids[i][viewmodel.currentRound.value!! - 1] = adapter.obtainBids()[i]
                }
                binding.textViewGameProcess.text = getString(R.string.playing)
                binding.buttonNext.visibility = View.GONE
                binding.buttonDone.visibility = View.VISIBLE
                val rv = root.findViewById<RecyclerView>(R.id.bid_win_table)
                val lm = rv.layoutManager as LinearLayoutManager
                for(i in 0 until viewmodel.playerCount){
                    if(i >= lm.findFirstVisibleItemPosition() && i <= lm.findLastVisibleItemPosition()){
                        rv.findViewWithTag<TextInputEditText>("b$i").isEnabled = false
                        rv.findViewWithTag<TextInputEditText>("w$i").isEnabled = true
                    }
                }
            }
        }

        binding.buttonDone.setOnClickListener{
            var error = false
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            if(adapter.obtainWins().sum() != viewmodel.currentRound.value){
                builder
                    .setMessage("Number of wins must be equal to the number of current round.")
                    .setTitle("Rule Violation")
                    .setNeutralButton("Okay") { dialog, which -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            else{
                for(i in 0 until viewmodel.playerCount){
                    if(adapter.obtainWins()[i] > viewmodel.currentRound.value!!){
                        error = true
                        builder
                            .setMessage("Player's number of bids cannot be more than the number of current round.")
                            .setTitle("Rule Violation")
                            .setPositiveButton("Okay") { dialog, which -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                }
            }
            if(!error){
                for(i in 0 until viewmodel.playerCount){
                    viewmodel.playerBids[i][viewmodel.currentRound.value!! - 1] = adapter.obtainBids()[i]
                }
                binding.textViewGameProcess.text = getString(R.string.bidding)
                binding.buttonDone.visibility = View.GONE
                binding.buttonNext.visibility = View.VISIBLE
                adapter.resetArrays()
                val rv = root.findViewById<RecyclerView>(R.id.bid_win_table)
                for(i in 0 until viewmodel.playerCount){
                    rv.findViewWithTag<TextInputEditText>("b$i").isEnabled = true
                    rv.findViewWithTag<TextInputEditText>("w$i").isEnabled = false
                }

                if(viewmodel.currentRound.value == viewmodel.rounds){
                    viewmodel.endGame()
                }
                else{
                    viewmodel.currentRound.value!!.plus(1)
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}