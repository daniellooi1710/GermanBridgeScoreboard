package com.example.germanbridgescoreboard.ui.gameinit

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentGameInitBinding
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.ui.home.HomeFragment


class InputPlayerFragment : Fragment(), InputPlayerRecyclerViewAdapter.Callbacks {
    private val viewmodel: MainViewModel by activityViewModels()
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_init, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                this.setItemViewCacheSize(12)
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = InputPlayerRecyclerViewAdapter(viewmodel.playerCount)
            }
        }
        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
    }

    override fun handleUserData(data: Array<String>) {
        for(i in 0..<viewmodel.playerCount){
            viewmodel.players[i] = data[i]
        }
    }
}