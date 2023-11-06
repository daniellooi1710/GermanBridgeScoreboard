package com.example.germanbridgescoreboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.germanbridgescoreboard.Game
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentHomeBinding
import com.example.germanbridgescoreboard.ui.gameinit.InputPlayerFragment

class HomeFragment : Fragment() {
    private lateinit var viewmodel: HomeViewModel
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

        val playerNumDisplay: EditText = binding.editTextNumber
        homeViewModel.playerNum.observe(viewLifecycleOwner, Observer {
            playerNumDisplay.setText(it.toString())
        })

        binding.button2.setOnClickListener{
            if (playerNumDisplay.text.toString().toInt() > 2) homeViewModel.minus()
            else{
                Toast.makeText(root.context, "Minimum number of players is 2", Toast.LENGTH_LONG).show()
            }
        }

        binding.button3.setOnClickListener{
            if (playerNumDisplay.text.toString().toInt() < 12) homeViewModel.add()
            else{
                Toast.makeText(root.context, "Maximum number of players is 12", Toast.LENGTH_LONG).show()
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