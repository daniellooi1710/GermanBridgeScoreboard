package com.example.germanbridgescoreboard.ui.scoreboard

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.GONE
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
        else root.findViewById<TextView>(R.id.textView4).visibility = View.VISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}