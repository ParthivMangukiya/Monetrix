package com.hackademy.monetrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.Transaction

class TransactionAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var transactions = emptyList<Transaction>() // Cached copy of words

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionView: TextView = itemView.findViewById(R.id.entry_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val transactionView = inflater.inflate(R.layout.entryitem, parent, false)
        return TransactionViewHolder(transactionView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = transactions[position]
        holder.transactionView.text = current.entry.amount.toString()
    }

    internal fun setTransactions(transactions: List<Transaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun getItemCount() = transactions.size
}