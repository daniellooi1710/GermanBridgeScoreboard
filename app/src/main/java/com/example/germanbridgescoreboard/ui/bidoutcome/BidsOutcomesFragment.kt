package com.example.germanbridgescoreboard.ui.bidoutcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentBidsOutcomesBinding
import com.google.android.material.textfield.TextInputEditText

class BidsOutcomesFragment : Fragment() {
    private val viewmodel: MainViewModel by activityViewModels()
    private val viewmodel2 = BidsOutcomesViewModel()
    private var _binding: FragmentBidsOutcomesBinding? = null
    lateinit var globalAdapter : BidsOutcomesRecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidsOutcomesBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
                adapter = BidsOutcomesRecyclerViewAdapter(viewmodel.playerCount, nameArray, viewmodel.gameProcess.value!!)
                globalAdapter = adapter as BidsOutcomesRecyclerViewAdapter
            }
        }

        viewmodel2.tempBidArr.clear()
        viewmodel2.tempWinArr.clear()

        viewmodel.currentRound.observe(viewLifecycleOwner) {
            binding.textViewRoundNumber.text = it.toString()
        }

        viewmodel.gameProcess.observe(viewLifecycleOwner) {
            if (viewmodel.gameStarted.value!!) {
                when (it) {
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
        }

        binding.buttonNext.setOnClickListener{
            var error = false
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            if(globalAdapter.obtainBids().sum() == viewmodel.currentRound.value){
                error = true
                builder
                    .setMessage("Total number of bids cannot be equal to the number of current round.")
                    .setTitle(getString(R.string.rule_violation))
                    .setNeutralButton("Okay") { _, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            else{
                for(i in 0 until viewmodel.playerCount){
                    if(globalAdapter.obtainBids()[i] > viewmodel.currentRound.value!!){
                        error = true
                        builder
                            .setMessage("Player's number of bids cannot be more than the number of current round.")
                            .setTitle(getString(R.string.rule_violation))
                            .setPositiveButton("Okay") { _, _ -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                    if(globalAdapter.obtainBids()[i] == -1){
                        error = true
                        builder
                            .setMessage("Player's number of bids cannot be null.")
                            .setTitle(getString(R.string.rule_violation))
                            .setPositiveButton("Okay") { _, _ -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                }
            }

            if(!error){
                for(i in 0 until viewmodel.playerCount) viewmodel.playerBids[i][viewmodel.currentRound.value!! - 1] = globalAdapter.obtainBids()[i]
                for(i in 0 until viewmodel.playerCount){
                    view2.findViewWithTag<TextInputEditText>("b$i").isEnabled = false
                    view2.findViewWithTag<TextInputEditText>("w$i").isEnabled = true
                }
                viewmodel.playing()
            }
        }

        binding.buttonDone.setOnClickListener{
            var error = false
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            if(globalAdapter.obtainWins().sum() != viewmodel.currentRound.value){
                error = true
                builder
                    .setMessage("Number of wins must be equal to the number of current round.")
                    .setTitle(getString(R.string.rule_violation))
                    .setNeutralButton("Okay") { _, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            else{
                for(i in 0 until viewmodel.playerCount){
                    if(globalAdapter.obtainWins()[i] > viewmodel.currentRound.value!!){
                        error = true
                        builder
                            .setMessage("Player's number of wins cannot be more than the number of current round.")
                            .setTitle(getString(R.string.rule_violation))
                            .setPositiveButton("Okay") { _, _ -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                    if(globalAdapter.obtainWins()[i] == -1){
                        error = true
                        builder
                            .setMessage("Player's number of wins cannot be null.")
                            .setTitle(getString(R.string.rule_violation))
                            .setPositiveButton("Okay") { _, _ -> }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        break
                    }
                }
            }

            if(!error){
                for(i in 0 until viewmodel.playerCount) viewmodel.playerWins[i][viewmodel.currentRound.value!! - 1] = globalAdapter.obtainWins()[i]
                viewmodel.calcScores()
                viewmodel.bidding()
                globalAdapter.resetArrays()
                for(i in 0 until viewmodel.playerCount){
                    view2.findViewWithTag<TextInputEditText>("b$i").isEnabled = true
                    view2.findViewWithTag<TextInputEditText>("b$i").setText("")
                    view2.findViewWithTag<TextInputEditText>("w$i").setText("")
                    view2.findViewWithTag<TextInputEditText>("w$i").isEnabled = false
                }
                if(viewmodel.currentRound.value == viewmodel.rounds) viewmodel.endGame() else viewmodel.nextRound()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}