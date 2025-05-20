package com.germanbridgescoreboard.ui.gameinit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.R
import com.germanbridgescoreboard.databinding.FragmentBidsOutcomesBinding
import com.germanbridgescoreboard.databinding.FragmentGameInitBinding
import com.germanbridgescoreboard.databinding.FragmentInputBinding


class InputPlayerFragment : Fragment(){
    private val viewmodel: MainViewModel by activityViewModels()
    private var _binding: FragmentGameInitBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameInitBinding.inflate(layoutInflater)
        val view = binding.root

        // Set the adapter
        with(view) {
            this.setItemViewCacheSize(12)
            layoutManager = LinearLayoutManager(context)
            adapter = InputPlayerRecyclerViewAdapter(viewmodel.playerCount)
        }
        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}