package com.hackademy.monetrix.ui.feed

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.model.Feed
import com.hackademy.monetrix.ui.adapter.CategoryLegendAdapter
import com.hackademy.monetrix.ui.adapter.FeedAdapter
import com.hackademy.monetrix.util.GridSpacingItemDecorator


class FeedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feed, container, false)
        recyclerView = root.findViewById(R.id.feedRecyclerView)
        adapter = FeedAdapter(requireContext())
        val feedList: List<Feed> = listOf (
            Feed("mutualfunds","Mutual Funds | Investments - HSBC IN"),
            Feed("homeloan","Home Loan: Apply for a Home Loan Online - HSBC IN")
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setValues(feedList)
        return root
    }
}