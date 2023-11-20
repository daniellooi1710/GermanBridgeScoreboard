package com.germanbridgescoreboard.ui.scoreboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.TableView
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.R
import com.germanbridgescoreboard.databinding.FragmentScoreboardBinding

class ScoreboardFragment : Fragment() {
    private val viewmodel : MainViewModel by activityViewModels()
    private var _binding: FragmentScoreboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btRanking = root.findViewById<Button>(R.id.buttonViewRanking)
        btRanking.setBackgroundColor(Color.rgb(69, 50, 175))
        btRanking.visibility = View.VISIBLE

        val tableView = root.findViewById<TableView>(R.id.tableView)

        if(viewmodel.gameProcess.value != MainViewModel.GAMEPROCESS.INIT){
            val adapter = ScoreboardTableViewAdapter(viewmodel.total)
            tableView.setAdapter(adapter)

            root.findViewById<TextView>(R.id.textView4).visibility = View.GONE

            val ch = ArrayList<Int>()
            for(i in 1 .. viewmodel.rounds) ch.add(i)
            val ch2 = ch as MutableList<Int>
            val rh = viewmodel.players
            val rh2 = MutableList(viewmodel.players.size){""}
            for(i in 0 until viewmodel.players.size){
                rh2[i] = rh[i]
            }
            val cellsT = viewmodel.playerScoresT
            val cells2T = MutableList(viewmodel.rounds){MutableList(viewmodel.players.size){0} }
            for (rounds in 0 until cells2T.size){
                for (players in 0 until cells2T[rounds].size){
                    cells2T[rounds][players] = cellsT[rounds][players]
                }
            }
            adapter.setAllItems(rh2, ch2, cells2T) // swapped row and column
        }
        else {
            btRanking.visibility = View.GONE
            root.findViewById<TextView>(R.id.textView4).visibility = View.VISIBLE
        }

        btRanking.setOnClickListener {
            var pNameR = viewmodel.players.clone()
            var pTotalR = viewmodel.total.clone()

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}