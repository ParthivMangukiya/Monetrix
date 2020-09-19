package com.hackademy.monetrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.Transaction
import com.hackademy.monetrix.util.UIUtil.getFormatted
import com.hackademy.monetrix.util.UIUtil.getResource

class TransactionAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var transactions = emptyList<Transaction>() // Cached copy of words

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.targetDateTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val categoryImageView: ImageView = itemView.findViewById(R.id.categoryImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val transactionView = inflater.inflate(R.layout.entryitem, parent, false)
        return TransactionViewHolder(transactionView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = transactions[position]
        holder.amountTextView.text = current.entry.amount.toString()
        holder.dateTextView.text = current.entry.date.getFormatted("yyyy-MM-dd")
        context.getResource(current.category.image)?.let {
            holder.categoryImageView.setImageDrawable(it)
        }
        holder.descriptionTextView.text = current.entry.description
    }

    internal fun setTransactions(transactions: List<Transaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun getItemCount() = transactions.size
}