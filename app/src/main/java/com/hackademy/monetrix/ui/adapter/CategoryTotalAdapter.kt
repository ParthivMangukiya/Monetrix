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
import com.hackademy.monetrix.util.Util.getResource
import com.hackademy.monetrix.util.Util.toRupee

class CategoryTotalAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<CategoryTotalAdapter.CategoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var categories = emptyList<CategoryTotal>() // Cached copy of words
    private var selectedPosition: Int = 0

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.category_image)
        val name: TextView = itemView.findViewById(R.id.entry_type)
        val amount: TextView = itemView.findViewById(R.id.amount)
//        val cardView: MaterialCardView = itemView.findViewById(R.id.categoryTotalCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryView = inflater.inflate(R.layout.item_category_total, parent, false)
        return CategoryViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = categories[position]
        holder.name.text = current.category.name
        holder.amount.text = current.total.toRupee()
        context.getResource(current.category.image)?.let {
            holder.image.setImageDrawable(it)
        }
        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    internal fun setCategories(categories: List<CategoryTotal>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount() = categories.size
}