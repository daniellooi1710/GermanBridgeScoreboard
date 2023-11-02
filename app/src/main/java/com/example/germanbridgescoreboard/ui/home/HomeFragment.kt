package com.example.germanbridgescoreboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.germanbridgescoreboard.Game
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        inflater.inflate(R.layout.fragment_home, container, false)

        binding.button2.setOnClickListener{
            var num = binding.editTextNumber.text.toString().toInt()
            if (num > 2) {
                num--
                binding.editTextNumber.setText(num.toString())
            }
        }

        binding.button3.setOnClickListener{
            var num = binding.editTextNumber.text.toString().toInt()
            if (num < 12) {
                num++
                binding.editTextNumber.setText(num.toString())
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun minusNum(view: View) {
        var num = view.findViewById<EditText>(R.id.editTextNumber).text.toString().toInt()
        if (num > 2) {
            num--
            view.findViewById<EditText>(R.id.editTextNumber).setText(num.toString())
        }
    }

    fun addNum(view: View) {
        var num = view.findViewById<EditText>(R.id.editTextNumber).text.toString().toInt()
        if (num < 12) {
            num++
            view.findViewById<EditText>(R.id.editTextNumber).setText(num.toString())
        }
    }

}