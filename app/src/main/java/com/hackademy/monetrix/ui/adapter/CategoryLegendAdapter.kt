package com.hackademy.monetrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.util.Util.getResource

class CategoryLegendAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<CategoryLegendAdapter.CategoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var categories = emptyList<Category>()
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
        val backgroudView: View = itemView.findViewById(R.id.view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryView = inflater.inflate(R.layout.item_category_legend, parent, false)
        return CategoryViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = categories[position]
        holder.name.text = current.name
        holder.backgroudView.setBackgroundColor(colors[position])
        context.getResource(current.image)?.let {
            holder.image.setImageDrawable(it)
        }
    }

    internal fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount() = categories.size
}