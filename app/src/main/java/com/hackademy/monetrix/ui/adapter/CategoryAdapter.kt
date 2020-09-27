package com.hackademy.monetrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.util.Util.getResource

class CategoryAdapter internal constructor(
    val context: Context,
    val onClick: (Long) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var categories = emptyList<Category>() // Cached copy of words
    private var selectedPosition: Int = 0

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.category_image)
        val name: TextView = itemView.findViewById(R.id.category_name)
        val backgroudView: View = itemView.findViewById(R.id.view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryView = inflater.inflate(R.layout.categoryitem, parent, false)
        return CategoryViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = categories[position]
        holder.name.text = current.name
        if (selectedPosition == position) {
            holder.backgroudView.background =
                ActivityCompat.getDrawable(context, R.drawable.category_bg_selected)
            onClick(current.id)
        } else {
            holder.backgroudView.background =
                ActivityCompat.getDrawable(context, R.drawable.category_bg)
        }
        context.getResource(current.image)?.let {
            holder.image.setImageDrawable(it)
        }
        holder.itemView.setOnClickListener {
            selectedPosition = position
            onClick(current.id)
            notifyDataSetChanged()
        }
    }

    internal fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount() = categories.size
}