package com.example.germanbridgescoreboard.ui.bidoutcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentBidsOutcomesBinding
import com.google.android.material.textfield.TextInputEditText

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

        val view2 = root.findViewById<RecyclerView>(R.id.bid_win_table)

        if(viewmodel.playerCount == 0) {
            binding.buttonNext.visibility = View.GONE
            binding.textViewGameProcess.text = getString(R.string.game_not_started)
        }

        if(viewmodel.playerCount > 0) {
            val nameArray = viewmodel.players
            with(view2) {
                this.setItemViewCacheSize(12)
                this.isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context)
                this.adapter = BidsOutcomesRecyclerViewAdapter(viewmodel.playerCount, nameArray)
                adapter = this.adapter as BidsOutcomesRecyclerViewAdapter
            }
        }

        viewmodel.currentRound.observe(viewLifecycleOwner, Observer {
            binding.textViewRoundNumber.text = it.toString()
        })

        viewmodel.gameProcess.observe(viewLifecycleOwner, Observer {
            if(viewmodel.gameStarted.value!!){
                when(it){
                    MainViewModel.GAMEPROCESS.BIDDING -> {
                        binding.textViewGameProcess.text = getString(R.string.bidding)
                        binding.buttonDone.visibility = View.GONE
                        binding.buttonNext.visibility = View.VISIBLE
                    }
                    MainViewModel.GAMEPROCESS.PLAYING -> {
                        binding.textViewGameProcess.text = getString(R.string.playing)
                        binding.buttonNext.visibility = View.GONE
                        binding.buttonDone.visibility = View.VISIBLE
                    }
                }
            }
        })

        binding.buttonNext.setOnClickListener{
            var error = false
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            if(adapter.obtainBids().sum() == viewmodel.currentRound.value){
                error = true
                builder
                    .setMessage("Total number of bids cannot be equal to the number of current round.")
                    .setTitle(getString(R.string.rule_violation))
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
                            .setTitle(getString(R.string.rule_violation))
                            .setPositiveButton("Okay") { dialog, which -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                }
            }
            if(!error){
                val array = adapter.obtainBids().toIntArray()
                for(i in 0 until viewmodel.playerCount){
                    viewmodel.playerBids[i][viewmodel.currentRound.value!! - 1] = array[i]
                }
                viewmodel.playing()

                val lm = view2.layoutManager as LinearLayoutManager
                for(i in 0 until viewmodel.playerCount){
                    view2.findViewWithTag<TextInputEditText>("b$i").isEnabled = false
                    view2.findViewWithTag<TextInputEditText>("w$i").isEnabled = true
                }
            }
        }

        binding.buttonDone.setOnClickListener{
            var error = false
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            if(adapter.obtainWins().sum() != viewmodel.currentRound.value){
                error = true
                builder
                    .setMessage("Number of wins must be equal to the number of current round.")
                    .setTitle(getString(R.string.rule_violation))
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
                            .setTitle(getString(R.string.rule_violation))
                            .setPositiveButton("Okay") { dialog, which -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                }
            }
            if(!error){
                for(i in 0 until viewmodel.playerCount){
                    viewmodel.playerWins[i][viewmodel.currentRound.value!! - 1] = adapter.obtainWins()[i]
                    Log.d("wins", adapter.obtainWins()[i].toString())
                    Log.d("inwins", viewmodel.playerWins[i][viewmodel.currentRound.value!! - 1].toString())
                }
                viewmodel.calcScores()
                viewmodel.bidding()
                for(i in 0 until viewmodel.playerCount){
                    view2.findViewWithTag<TextInputEditText>("b$i").isEnabled = true
                    view2.findViewWithTag<TextInputEditText>("b$i").setText("")
                    view2.findViewWithTag<TextInputEditText>("w$i").setText("")
                    view2.findViewWithTag<TextInputEditText>("w$i").isEnabled = false
                }
                adapter.resetArrays()
                if(viewmodel.currentRound.value == viewmodel.rounds){
                    viewmodel.endGame()
                }
                else{
                    viewmodel.nextRound()
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