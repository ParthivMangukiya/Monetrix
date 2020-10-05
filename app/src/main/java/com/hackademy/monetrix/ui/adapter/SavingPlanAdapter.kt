package com.hackademy.monetrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.SavingPlanTransaction
import com.hackademy.monetrix.util.Util.getFormatted
import com.hackademy.monetrix.util.Util.toRupee

class SavingPlanAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<SavingPlanAdapter.TransactionViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var savingPlans = emptyList<SavingPlanTransaction>() // Cached copy of words

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goalTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val savedTextView: TextView = itemView.findViewById(R.id.savedTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val targetDateTextView: TextView = itemView.findViewById(R.id.targetDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val savingPlanView = inflater.inflate(R.layout.item_saving_plan, parent, false)
        return TransactionViewHolder(savingPlanView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = savingPlans[position]
        holder.goalTextView.text = "Goal: " + current.savingPlan.amount.toRupee()
        holder.savedTextView.text = "Saved: " + current.savedAmount.toRupee()
        holder.nameTextView.text = current.savingPlan.name
        if(!current.savingPlan.completed) {
            holder.targetDateTextView.text = "Target: " + current.savingPlan.targetDate.getFormatted("yyyy-MM-dd")
        } else {
            holder.targetDateTextView.text = "Achieved: 05-10-2020"
        }
    }

    internal fun setSavingPlans(savingPlans: List<SavingPlanTransaction>) {
        this.savingPlans = savingPlans
        notifyDataSetChanged()
    }

    override fun getItemCount() = savingPlans.size
}