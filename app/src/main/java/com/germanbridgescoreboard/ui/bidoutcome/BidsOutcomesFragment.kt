package com.germanbridgescoreboard.ui.bidoutcome

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.shawnlin.numberpicker.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.germanbridgescoreboard.MainActivity
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.R
import com.germanbridgescoreboard.databinding.FragmentBidsOutcomesBinding
import com.germanbridgescoreboard.ui.scoreboard.RankingRecyclerViewAdapter

class BidsOutcomesFragment : Fragment() {
    private val viewmodel : MainViewModel by activityViewModels()
    private var _binding : FragmentBidsOutcomesBinding? = null
    private lateinit var globalAdapter : BidsOutcomesRecyclerViewAdapter
    private lateinit var mainAct : MainActivity

    private val binding get() = _binding!!

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.rule_violation))
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ -> }
            .create()
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidsOutcomesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val view2 = binding.bidWinTable

        mainAct = activity as MainActivity

        if(viewmodel.playerCount > 0) {
            val nameArray = viewmodel.players
            with(view2) {
                this.setItemViewCacheSize(12)
                this.isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context)

                val currentRoundBids = Array(viewmodel.playerCount){0}
                val currentRound = viewmodel.currentRound
                for (i in 0 until viewmodel.playerCount) currentRoundBids[i] = viewmodel.playerBids[i][viewmodel.currentRound.value!! - 1]

                adapter = BidsOutcomesRecyclerViewAdapter(
                    viewmodel.playerCount,
                    nameArray,
                    viewmodel.gameProcess.value!!,
                    currentRoundBids,
                    currentRound
                )
                globalAdapter = adapter as BidsOutcomesRecyclerViewAdapter
            }
        }

        viewmodel.currentRound.observe(viewLifecycleOwner) {
            binding.textViewRoundNumber.text = it.toString()
        }

        viewmodel.gameProcess.observe(viewLifecycleOwner) {
            when (it) {
                MainViewModel.GAMEPROCESS.INIT -> {
                    binding.textViewGameProcess.text = getString(R.string.game_not_started)
                    binding.textViewGameProcess.setTextColor(Color.RED)
                    binding.buttonNext.visibility = View.GONE
                    binding.buttonDone.visibility = View.GONE
                    binding.buttonBack.visibility = View.GONE
                }
                MainViewModel.GAMEPROCESS.BIDDING -> {
                    binding.textViewGameProcess.text = getString(R.string.bidding)
                    binding.textViewGameProcess.setTextColor(Color.rgb(200, 88, 5))
                    binding.buttonDone.visibility = View.GONE
                    binding.buttonNext.visibility = View.VISIBLE
                    binding.buttonBack.visibility = View.GONE
                }
                MainViewModel.GAMEPROCESS.PLAYING -> {
                    binding.textViewGameProcess.text = getString(R.string.playing)
                    binding.textViewGameProcess.setTextColor(Color.rgb(5, 100, 5))
                    binding.buttonNext.visibility = View.GONE
                    binding.buttonDone.visibility = View.VISIBLE
                    binding.buttonBack.visibility = View.VISIBLE
                }
                MainViewModel.GAMEPROCESS.ENDED -> {
                    binding.textViewGameProcess.text = getString(R.string.game_ended)
                    binding.textViewGameProcess.setTextColor(Color.rgb(25, 1, 100))
                    binding.buttonNext.visibility = View.GONE
                    binding.buttonDone.visibility = View.GONE
                    binding.buttonBack.visibility = View.GONE
                }
            }
        }

        binding.buttonNext.setOnClickListener{
            var error = false
            if(globalAdapter.obtainBids().sum() == viewmodel.currentRound.value){
                error = true
                showErrorDialog("Total number of bids cannot be equal to the number of current round.")
            }

            if(!error){
                for(i in 0 until viewmodel.playerCount) {
                    viewmodel.playerBids[i][viewmodel.currentRound.value!! - 1] =
                        globalAdapter.obtainBids()[i]
                    view2.findViewWithTag<NumberPicker>("b$i").isEnabled = false
                    view2.findViewWithTag<NumberPicker>("b$i").selectedTextColor = Color.GRAY
                    view2.findViewWithTag<NumberPicker>("w$i").isEnabled = true
                    view2.findViewWithTag<NumberPicker>("w$i").selectedTextColor = Color.BLACK
                }
                mainAct.updateDatabase()
                viewmodel.playing()
            }
        }

        binding.buttonDone.setOnClickListener{
            var error = false
            if(globalAdapter.obtainWins().sum() != viewmodel.currentRound.value){
                error = true
                showErrorDialog("Number of wins must be equal to the number of current round.")
            }

            if(!error){
                for(i in 0 until viewmodel.playerCount){
                    viewmodel.playerWins[i][viewmodel.currentRound.value!! - 1] = globalAdapter.obtainWins()[i]
                    view2.findViewWithTag<NumberPicker>("b$i").isEnabled = true
                    view2.findViewWithTag<NumberPicker>("b$i").selectedTextColor = Color.BLACK
                    view2.findViewWithTag<NumberPicker>("b$i").value = 0
                    view2.findViewWithTag<NumberPicker>("w$i").isEnabled = false
                    view2.findViewWithTag<NumberPicker>("w$i").selectedTextColor = Color.GRAY
                    view2.findViewWithTag<NumberPicker>("w$i").value = 0
                }
                viewmodel.calcScores()
                viewmodel.bidding()
                globalAdapter.resetArrays()
                mainAct.updateDatabase()
                if(viewmodel.currentRound.value != viewmodel.rounds) {
                    viewmodel.nextRound()
                    for(i in 0 until viewmodel.playerCount){
                        view2.findViewWithTag<NumberPicker>("b$i").maxValue = viewmodel.currentRound.value!!
                        view2.findViewWithTag<NumberPicker>("w$i").maxValue = viewmodel.currentRound.value!!
                    }
                }
                else{
                    viewmodel.endGame()
                    showRankings()
                }
            }
        }

        binding.buttonBack.setOnClickListener{
            viewmodel.bidding()
            for(i in 0 until viewmodel.playerCount){
                viewmodel.playerWins[i][viewmodel.currentRound.value!! - 1] = globalAdapter.obtainWins()[i]
                view2.findViewWithTag<NumberPicker>("b$i").isEnabled = true
                view2.findViewWithTag<NumberPicker>("b$i").selectedTextColor = Color.BLACK
                view2.findViewWithTag<NumberPicker>("w$i").value = 0
                view2.findViewWithTag<NumberPicker>("w$i").isEnabled = false
                view2.findViewWithTag<NumberPicker>("w$i").selectedTextColor = Color.GRAY
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showRankings(){
        val pNameR = viewmodel.players.clone()
        val pTotalR = viewmodel.total.clone()

        for(i in 0 until (viewmodel.playerCount - 1)) {
            var swapped = false
            for (j in 0 until (viewmodel.playerCount - i - 1)) {
                if (pTotalR[j] < pTotalR[j + 1]){
                    val tempTotal = pTotalR[j]
                    pTotalR[j] = pTotalR[j + 1]
                    pTotalR[j + 1] = tempTotal

                    val tempName = pNameR[j]
                    pNameR[j] = pNameR[j + 1]
                    pNameR[j + 1] = tempName
                    swapped = true
                }
            }

            // If no two elements were swapped by inner loop, then break
            if (!swapped) break
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_ranking_list, null)
        val rv = dialogView.findViewById<RecyclerView>(R.id.rankingRecyclerView)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = RankingRecyclerViewAdapter(requireContext(), viewmodel.playerCount, pNameR, pTotalR)
        builder
            .setTitle("Game Rankings")
            .setView(dialogView)
            .setPositiveButton("Okay") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.show()
    }
}