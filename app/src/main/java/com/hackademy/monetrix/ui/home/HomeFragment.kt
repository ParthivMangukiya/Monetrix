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
import com.hackademy.monetrix.ui.adapter.CategoryTotalAdapter
import com.hackademy.monetrix.ui.adapter.TransactionAdapter
import com.hackademy.monetrix.util.Constants
import com.hackademy.monetrix.util.GridSpacingItemDecorator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var pieChart: PieChart
    private lateinit var balanceChart: PieChart
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryTotalAdapter


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
        balanceChart = root.findViewById(R.id.balancechart)
        recyclerView = root.findViewById(R.id.category_total_recyclerview)
        setupPieChart()
        setupBalanceChart()
        setupRecyclerView()

        val colors = arrayOf(
            ContextCompat.getColor(requireContext(), R.color.chart_color1),
            ContextCompat.getColor(requireContext(), R.color.chart_color2),
            ContextCompat.getColor(requireContext(), R.color.chart_color3),
            ContextCompat.getColor(requireContext(), R.color.chart_color4),
            ContextCompat.getColor(requireContext(), R.color.chart_color5),
            ContextCompat.getColor(requireContext(), R.color.chart_color6),
            ContextCompat.getColor(requireContext(), R.color.chart_color7),
            ContextCompat.getColor(requireContext(), R.color.chart_color8)
        )

        val balanceColors = arrayOf(
            ContextCompat.getColor(requireContext(), R.color.income),
            ContextCompat.getColor(requireContext(), R.color.expense)
        )

        homeViewModel.categoriesTotal.observe(viewLifecycleOwner, Observer { list ->
            val entries: List<PieEntry> = list.map {
                PieEntry(it.total.toFloat(), it.category.name)
            }
            val set = PieDataSet(entries, "Categories")
            set.colors = colors.toMutableList()
            val data = PieData(set)
            data.setValueTextSize(Constants.chartValueSize)
            pieChart.setExtraOffsets(0f, 0f, 0f, -pieChart.height.toFloat() * 0.35f)
            pieChart.data = data
            pieChart.animateX(2000, Easing.EaseOutSine)
            adapter.setCategories(list)
        })

        homeViewModel.entryTotal.observe(viewLifecycleOwner, Observer { list ->
            val entries: List<PieEntry> = list.map {
                PieEntry(it.total.toFloat(), it.type.name)
            }
            val set = PieDataSet(entries, "Categories")
            set.colors = balanceColors.toMutableList()
            val data = PieData(set)
            data.setValueTextSize(Constants.chartValueSize)
            balanceChart.setExtraOffsets(0f, 0f, 0f, -balanceChart.height.toFloat() * 0.35f)
            balanceChart.data = data
            balanceChart.animateX(1500, Easing.EaseOutSine)
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                homeViewModel.showIncome.value = (position == 0)
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
        pieChart.holeRadius = 80f
        pieChart.isRotationEnabled = false
        pieChart.maxAngle = 180.0F
        pieChart.rotationAngle = 180.0f
        pieChart.setHoleColor(Color.parseColor("#00000000"))
    }

    private fun setupBalanceChart() {
        balanceChart.legend.isEnabled = false
        balanceChart.description = null
        balanceChart.setDrawSlicesUnderHole(false)
        balanceChart.setDrawEntryLabels(false)
        balanceChart.setTransparentCircleAlpha(0)
        balanceChart.holeRadius = 70f
        balanceChart.isRotationEnabled = false
        balanceChart.maxAngle = 180.0F
        balanceChart.rotationAngle = 180.0f
        balanceChart.setHoleColor(Color.parseColor("#00000000"))
    }
}