package com.example.germanbridgescoreboard.ui.bidoutcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.germanbridgescoreboard.databinding.FragmentBidsOutcomesBinding
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.ui.gameinit.InputPlayerRecyclerViewAdapter

class BidsOutcomesFragment : Fragment() {
    private val viewmodel: MainViewModel by activityViewModels()
    private var _binding: FragmentBidsOutcomesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bids_outcomes, container, false)
        Log.d("debug", "good")
        // Set the adapter
        val view2 = view.findViewById<RecyclerView>(R.id.bid_win_table)
        if (view2 is RecyclerView) {
            with(view2) {
                this.setItemViewCacheSize(12)
                layoutManager = LinearLayoutManager(context)
                if(viewmodel.playerCount > 0) {
                    val nameArray = viewmodel.players
                    adapter = BidsOutcomesRecyclerViewAdapter(viewmodel.playerCount, nameArray)
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}