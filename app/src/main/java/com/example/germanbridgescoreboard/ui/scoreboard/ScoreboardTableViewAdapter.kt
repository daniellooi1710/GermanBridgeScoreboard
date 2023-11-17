package com.example.germanbridgescoreboard.ui.scoreboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.example.germanbridgescoreboard.R

/*
class ScoreboardTableViewAdapter : AbstractTableAdapter<Int, String, Int>() {

    inner class ScoreboardCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
        val cellContainer = itemView.findViewById<LinearLayout>(R.id.cell_container)
        val cellTextView = itemView.findViewById<TextView>(R.id.cell_data)
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
        val columnHeaderContainer = itemView.findViewById<LinearLayout>(R.id.column_header_container)
        val columnHeaderTextView = itemView.findViewById<TextView>(R.id.column_header_textView)
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
        columnHeaderItemModel: Int?,
        columnPosition: Int
    ) {
        val columnHeader = columnHeaderItemModel!!

        val viewHolder: ScoreboardColumnHeaderViewHolder = holder as ScoreboardColumnHeaderViewHolder
        viewHolder.columnHeaderTextView.text = columnHeader.toString()
    }

    inner class ScoreboardRowHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView){
        val rowHeaderContainer = itemView.findViewById<LinearLayout>(R.id.row_header_container)
        val rowHeaderTextView = itemView.findViewById<TextView>(R.id.row_header_textView)
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
        rowHeaderItemModel: String?,
        rowPosition: Int
    ) {
        val rowHeader = rowHeaderItemModel!!

        val viewHolder: ScoreboardRowHeaderViewHolder = holder as ScoreboardRowHeaderViewHolder
        viewHolder.rowHeaderTextView.text = rowHeader
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.table_view_corner_layout, parent, false)
    }

}*/

class ScoreboardTableViewAdapter(val totals: Array<Int>) : AbstractTableAdapter<String, Int, Int>() {

    inner class ScoreboardCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
        val cellContainer = itemView.findViewById<LinearLayout>(R.id.cell_container)
        val cellTextView = itemView.findViewById<TextView>(R.id.cell_data)
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
        val columnHeaderContainer = itemView.findViewById<LinearLayout>(R.id.column_header_container)
        val columnHeaderTextView = itemView.findViewById<TextView>(R.id.column_header_textView)
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
        val rowHeaderContainer = itemView.findViewById<LinearLayout>(R.id.row_header_container)
        val rowHeaderTextView = itemView.findViewById<TextView>(R.id.row_header_textView)
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
