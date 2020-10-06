package com.hackademy.monetrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.Feed
import com.hackademy.monetrix.util.Util.getResource

class FeedAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var feedList = emptyList<Feed>()

    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.feedImage)
        val description: TextView = itemView.findViewById(R.id.detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val feedView = inflater.inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(feedView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = feedList[position]
        context.getResource(current.image)?.let {
            holder.image.setImageDrawable(it)
        }
        holder.description.text = current.description
    }

    internal fun setValues(feedList: List<Feed>) {
        this.feedList = feedList
        notifyDataSetChanged()
    }

    override fun getItemCount() = feedList.size
}