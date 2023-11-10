package com.example.germanbridgescoreboard.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.germanbridgescoreboard.Game
import com.example.germanbridgescoreboard.MainViewModel
import com.example.germanbridgescoreboard.R
import com.example.germanbridgescoreboard.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val viewmodel: MainViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewmodel.gameStarted.observe(viewLifecycleOwner, Observer{
            if(viewmodel.gameStarted.value!!){
                binding.textView.setBackgroundColor(Color.GREEN)
                binding.textView.setText(R.string.game_ongoing)
                binding.buttonMinus.isEnabled = false
                binding.buttonPlus.isEnabled = false
                binding.buttonStart.isEnabled = false
                binding.buttonNew.isEnabled = true
            }
            else{
                binding.textView.setBackgroundColor(Color.YELLOW)
                binding.textView.setText(R.string.init_game)
                binding.buttonStart.isEnabled = true
                binding.buttonNew.isEnabled = false
                binding.buttonMinus.isEnabled = true
                binding.buttonPlus.isEnabled = true
            }
        })

        val playerNumDisplay: EditText = binding.editTextNumber
        viewmodel.playerNum.observe(viewLifecycleOwner, Observer {
            playerNumDisplay.setText(it.toString())
        })

        binding.buttonMinus.setOnClickListener{
            if (playerNumDisplay.text.toString().toInt() > 2) viewmodel.minus()
            else{
                Toast.makeText(root.context, "Minimum number of players is 2", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonPlus.setOnClickListener{
            if (playerNumDisplay.text.toString().toInt() < 12) viewmodel.add()
            else{
                Toast.makeText(root.context, "Maximum number of players is 12", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonStart.setOnClickListener {
            viewmodel.initGame()
            it.findNavController().navigate(R.id.navigation_game_init)
        }

        binding.buttonNew.setOnClickListener {
            viewmodel.newGame()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}