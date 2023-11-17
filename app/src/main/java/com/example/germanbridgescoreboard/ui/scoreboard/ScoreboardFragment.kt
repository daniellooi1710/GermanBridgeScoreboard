package com.example.germanbridgescoreboard.ui.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.evrencoskun.tableview.TableView
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentScoreboardBinding

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

        val tableView = root.findViewById<TableView>(R.id.tableView)
        val adapter = ScoreboardTableViewAdapter(viewmodel.total)

        tableView.setAdapter(adapter)

        if(viewmodel.gameStarted.value!!){
            val ch = ArrayList<Int>()
            for(i in 1 .. viewmodel.rounds) ch.add(i)
            val ch2 = ch as MutableList<Int>
            val rh = viewmodel.players
            val rh2 = MutableList(viewmodel.players.size){""}
            for(i in 0 until viewmodel.players.size){
                rh2[i] = rh[i]
            }
            val cells = viewmodel.playerScores
            val cellsT = viewmodel.playerScoresT
            val cells2 = MutableList(viewmodel.players.size){MutableList(viewmodel.rounds){0} }
            val cells2T = MutableList(viewmodel.rounds){MutableList(viewmodel.players.size){0} }
            for (players in 0 until cells2.size){
                for (rounds in 0 until cells2[players].size){
                    cells2[players][rounds] = cells[players][rounds]
                }
            }
            for (rounds in 0 until cells2T.size){
                for (players in 0 until cells2T[rounds].size){
                    cells2T[rounds][players] = cellsT[rounds][players]
                }
            }
            adapter.setAllItems(rh2, ch2, cells2T) // swapped row and column
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}