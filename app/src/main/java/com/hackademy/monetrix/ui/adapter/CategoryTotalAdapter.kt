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

class CategoryTotalAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<CategoryTotalAdapter.CategoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var categories = emptyList<CategoryTotal>() // Cached copy of words
    private var selectedPosition: Int = 0

    private val colors = arrayOf(
        ContextCompat.getColor(context, R.color.chart_color1),
        ContextCompat.getColor(context, R.color.chart_color2),
        ContextCompat.getColor(context, R.color.chart_color3),
        ContextCompat.getColor(context, R.color.chart_color4),
        ContextCompat.getColor(context, R.color.chart_color5),
        ContextCompat.getColor(context, R.color.chart_color6),
        ContextCompat.getColor(context, R.color.chart_color7),
        ContextCompat.getColor(context, R.color.chart_color8)
    )

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.category_image)
        val name: TextView = itemView.findViewById(R.id.category_name)
        val cardView: MaterialCardView = itemView.findViewById(R.id.categoryTotalCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryView = inflater.inflate(R.layout.item_category_total, parent, false)
        return CategoryViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = categories[position]
        holder.name.text = current.category.name
        context.getResource(current.category.image)?.let {
            holder.image.setImageDrawable(it)
        }
        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }
        holder.cardView.setStrokeColor(colors[position])
    }

    internal fun setCategories(categories: List<CategoryTotal>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount() = categories.size
}