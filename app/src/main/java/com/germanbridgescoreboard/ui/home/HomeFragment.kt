package com.germanbridgescoreboard.ui.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.germanbridgescoreboard.MainViewModel
import com.germanbridgescoreboard.R
import com.germanbridgescoreboard.databinding.FragmentHomeBinding

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewmodel.gameProcess.observe(viewLifecycleOwner) {
            when(it){
                MainViewModel.GAMEPROCESS.INIT -> {
                    binding.textView.setBackgroundColor(Color.YELLOW)
                    binding.textView.setText(R.string.init_game)
                    binding.buttonStart.isEnabled = true
                    binding.buttonNew.isEnabled = false
                    binding.buttonMinus.isEnabled = true
                    binding.buttonPlus.isEnabled = true
                }
                MainViewModel.GAMEPROCESS.BIDDING, MainViewModel.GAMEPROCESS.PLAYING -> {
                    binding.textView.setBackgroundColor(Color.GREEN)
                    binding.textView.setText(R.string.game_ongoing)
                    binding.buttonMinus.isEnabled = false
                    binding.buttonPlus.isEnabled = false
                    binding.buttonStart.isEnabled = false
                    binding.buttonNew.isEnabled = true
                }
                MainViewModel.GAMEPROCESS.ENDED -> {
                    binding.textView.setBackgroundColor(Color.RED)
                    binding.textView.setText(R.string.game_ended)
                    binding.buttonMinus.isEnabled = false
                    binding.buttonPlus.isEnabled = false
                    binding.buttonStart.isEnabled = false
                    binding.buttonNew.isEnabled = true
                }
            }
        }

        val playerNumDisplay: EditText = binding.editTextNumber
        viewmodel.playerNum.observe(viewLifecycleOwner) {
            playerNumDisplay.setText(it.toString())
        }

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
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder
                .setMessage("End current game and start a new game?")
                .setTitle("New Game")
                .setPositiveButton("Yes") { _, _ ->
                    viewmodel.newGame()
                }
                .setNegativeButton("No") { _, _ ->
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}