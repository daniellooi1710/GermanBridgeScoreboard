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
import com.example.germanbridgescoreboard.ui.gameinit.InputPlayerFragment

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

        binding.button4.setOnClickListener {
            val num = binding.editTextNumber.text.toString().toInt()
            var game = Game(num)
            var fragment = InputPlayerFragment()
            var fm = activity?.supportFragmentManager
            var ft = fm?.beginTransaction()
            if (ft != null) {
                ft.replace(R.id.nav_host_fragment_activity_main, fragment)
                ft.addToBackStack(null)
                ft.commit()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}