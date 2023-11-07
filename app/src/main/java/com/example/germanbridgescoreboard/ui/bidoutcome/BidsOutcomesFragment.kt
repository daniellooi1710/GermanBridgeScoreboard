package com.example.germanbridgescoreboard.ui.bidoutcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.germanbridgescoreboard.databinding.FragmentBidsOutcomesBinding
import com.example.germanbridgescoreboard.MainViewModel

class BidsOutcomesFragment : Fragment() {
    private lateinit var viewmodel: MainViewModel
    private var _binding: FragmentBidsOutcomesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidsOutcomesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}