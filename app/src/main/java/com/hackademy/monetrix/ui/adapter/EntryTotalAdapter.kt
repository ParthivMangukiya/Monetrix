package com.hackademy.monetrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.CategoryTotal
import com.hackademy.monetrix.data.model.EntryTotal
import com.hackademy.monetrix.util.Util.getResource
import com.hackademy.monetrix.util.Util.toRupee

class EntryTotalAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<EntryTotalAdapter.EntryTotalViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var entryTotal = emptyList<EntryTotal>() // Cached copy of words
    private val colors = arrayOf(
        ContextCompat.getColor(context, R.color.chart_color9),
        ContextCompat.getColor(context, R.color.chart_color10),
        ContextCompat.getColor(context, R.color.chart_color11)
    )

    inner class EntryTotalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entrytype: TextView = itemView.findViewById(R.id.entry_type)
        val amount: TextView = itemView.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryTotalViewHolder {
        val categoryView = inflater.inflate(R.layout.item_entry_total, parent, false)
        return EntryTotalViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: EntryTotalViewHolder, position: Int) {
        val current = entryTotal[position]
        holder.entrytype.text = current.type.toString()
        holder.entrytype.setTextColor(colors[position])
        holder.amount.text = current.total.toRupee()
    }

    internal fun setValues(entryTotal: List<EntryTotal>) {
        this.entryTotal = entryTotal
        notifyDataSetChanged()
    }

    override fun getItemCount() = entryTotal.size
}