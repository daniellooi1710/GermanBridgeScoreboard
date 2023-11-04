package com.example.germanbridgescoreboard

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.germanbridgescoreboard.placeholder.PlaceholderContent.PlaceholderItem
import com.example.germanbridgescoreboard.databinding.FragmentInputBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class InputPlayerRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<InputPlayerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentInputBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentInputBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}