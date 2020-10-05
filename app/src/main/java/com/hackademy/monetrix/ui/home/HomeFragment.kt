package com.hackademy.monetrix.ui.home

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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.ui.adapter.CategoryTotalAdapter
import com.hackademy.monetrix.ui.adapter.EntryTotalAdapter
import com.hackademy.monetrix.util.Constants
import com.hackademy.monetrix.util.GridSpacingItemDecorator

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var pieChart: PieChart
    private lateinit var recyclerView: RecyclerView
    private lateinit var entryRecyclerView: RecyclerView
    private lateinit var adapter: CategoryTotalAdapter
    private lateinit var entryAdapter: EntryTotalAdapter
    private var animationDuration: Int = 500


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val tabLayout: TabLayout = root.findViewById(R.id.homeTabLayout)
        pieChart = root.findViewById(R.id.piechart)
        recyclerView = root.findViewById(R.id.category_total_recyclerview)
        entryRecyclerView = root.findViewById(R.id.entry_recyclerview)
        animationDuration = requireContext().resources.getInteger(R.integer.anim_duration_medium)
        setupPieChart()
        setupRecyclerView()

        val colors = arrayOf(
            ContextCompat.getColor(requireContext(), R.color.chart_color9),
            ContextCompat.getColor(requireContext(), R.color.chart_color10),
            ContextCompat.getColor(requireContext(), R.color.chart_color11)
        )

        homeViewModel.categoriesTotal.observe(viewLifecycleOwner, Observer { list ->
            adapter.setCategories(list)
            recyclerView.scheduleLayoutAnimation()
            pieChart.animateX(animationDuration, Easing.EaseOutSine)
        })

        homeViewModel.entryTotal.observe(viewLifecycleOwner, Observer { list ->
            val entries: List<PieEntry> = list.map {
                PieEntry(it.total.toFloat(), it.type.name)
            }
            val set = PieDataSet(entries, "By Type")
            set.colors = colors.toMutableList()
            set.setDrawValues(false)
            val data = PieData(set)
            data.setValueTextSize(Constants.chartValueSize)
            pieChart.data = data
            entryAdapter.setValues(list)
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                homeViewModel.showEntryType.value = when(position) {
                    0  -> EntryType.Income
                    1 -> EntryType.Expense
                    2 -> EntryType.Investment
                    else -> EntryType.Income
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return root
    }

    private fun setupRecyclerView() {
        adapter = CategoryTotalAdapter(requireContext())
        recyclerView.adapter = adapter
        val spanCount = 2
        val spacing = 16.toPx()
        recyclerView.addItemDecoration(GridSpacingItemDecorator(spanCount, spacing, true))
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), spanCount)

        entryAdapter = EntryTotalAdapter(requireContext())
        entryRecyclerView.adapter = entryAdapter
        entryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun Int.toPx(): Int {
        val displayMetrics: DisplayMetrics = requireContext().getResources().getDisplayMetrics();
        return this * displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    }

    private fun setupPieChart() {
        pieChart.legend.isEnabled = false
        pieChart.description = null
        pieChart.setDrawSlicesUnderHole(false)
        pieChart.setDrawEntryLabels(false)
        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 70f
        pieChart.isRotationEnabled = false
        pieChart.maxAngle = 270.0F
        pieChart.rotationAngle = 135.0f
        pieChart.setHoleColor(Color.parseColor("#00000000"))
    }
}