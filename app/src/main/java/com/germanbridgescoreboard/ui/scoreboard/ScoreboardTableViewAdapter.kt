package com.germanbridgescoreboard.ui.scoreboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.germanbridgescoreboard.R

class ScoreboardTableViewAdapter(private val totals: Array<Int>) : AbstractTableAdapter<String, Int, Int>() {

    inner class ScoreboardCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
        val cellTextView: TextView = itemView.findViewById(R.id.cell_data)
        init{
            super.itemView
        }
    }

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout: View = LayoutInflater.from(parent.context).inflate(R.layout.table_view_cell_layout, parent, false)
        return ScoreboardCellViewHolder(layout)
    }

    override fun onBindCellViewHolder(
        holder: AbstractViewHolder,
        cellItemModel: Int?,
        columnPosition: Int,
        rowPosition: Int
    ) {
        val cell = cellItemModel!!
        val viewHolder: ScoreboardCellViewHolder = holder as ScoreboardCellViewHolder
        viewHolder.cellTextView.text = cell.toString()
    }

    inner class ScoreboardColumnHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView){
        val columnHeaderTextView: TextView = itemView.findViewById(R.id.column_header_textView)
        init{
            super.itemView
        }
    }

    override fun onCreateColumnHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder {
        val layout: View = LayoutInflater.from(parent.context).inflate(R.layout.table_view_column_header_layout, parent, false)
        return ScoreboardColumnHeaderViewHolder(layout)
    }

    override fun onBindColumnHeaderViewHolder(
        holder: AbstractViewHolder,
        columnHeaderItemModel: String?,
        columnPosition: Int
    ) {
        val columnHeader = columnHeaderItemModel!!

        val viewHolder: ScoreboardColumnHeaderViewHolder = holder as ScoreboardColumnHeaderViewHolder
        viewHolder.columnHeaderTextView.text = columnHeader + "\n(${totals[columnPosition]})"
    }

    inner class ScoreboardRowHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView){
        val rowHeaderTextView: TextView = itemView.findViewById(R.id.row_header_textView)
        init{
            super.itemView
        }
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout: View = LayoutInflater.from(parent.context).inflate(R.layout.table_view_row_header_layout, parent, false)
        return ScoreboardRowHeaderViewHolder(layout)
    }

    override fun onBindRowHeaderViewHolder(
        holder: AbstractViewHolder,
        rowHeaderItemModel: Int?,
        rowPosition: Int
    ) {
        val rowHeader = rowHeaderItemModel!!

        val viewHolder: ScoreboardRowHeaderViewHolder = holder as ScoreboardRowHeaderViewHolder
        viewHolder.rowHeaderTextView.text = rowHeader.toString()
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.table_view_corner_layout, parent, false)
    }
}
