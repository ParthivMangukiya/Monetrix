package com.hackademy.monetrix.ui.insight

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
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.ui.adapter.CategoryLegendAdapter
import com.hackademy.monetrix.util.GridSpacingItemDecorator


class InsightFragment : Fragment() {

    private lateinit var insightViewModel: InsightViewModel
    private lateinit var pieChart: PieChart
    private lateinit var lineChart: LineChart
    private var animationDuration: Int = 500
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryLegendAdapter
    private lateinit var labels: List<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insightViewModel =
            ViewModelProvider(this).get(InsightViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_insight, container, false)
        val tabLayout: TabLayout = root.findViewById(R.id.homeTabLayout)
        recyclerView = root.findViewById(R.id.category_recyclerview)
        setupRecyclerView()
        pieChart = root.findViewById(R.id.piechart)
        lineChart = root.findViewById(R.id.linechart)
        animationDuration = requireContext().resources.getInteger(R.integer.anim_duration_medium)
        setupPieChart()
        setupLineChart()

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

        insightViewModel.categoriesTotal.observe(viewLifecycleOwner, Observer { list ->
            val entries: List<PieEntry> = list.map {
                PieEntry(it.total.toFloat(), it.category.name)
            }
            val set = PieDataSet(entries, "Categories")
            set.sliceSpace = 8f
            set.colors = colors.toMutableList()
            set.setDrawValues(false)
            val data = PieData(set)
            pieChart.data = data
            adapter.setCategories(list.map {
                it.category
            })
            pieChart.animateX(animationDuration, Easing.EaseOutSine)
        })

        insightViewModel.entryTotal.observe(viewLifecycleOwner, Observer { list ->
            labels = list.map {
                it.month
            }
            val entries: MutableList<Entry> = mutableListOf()
            list.forEachIndexed { index, entryTotalWithMonth -> entries.add(Entry(index.toFloat(), entryTotalWithMonth.total.toFloat())) }
            val set = LineDataSet(entries, "By Month")
            set.setDrawValues(false)
            set.setColor(Color.DKGRAY);
            set.setCircleColor(Color.DKGRAY);
            set.setLineWidth(1f);
            set.setCircleRadius(3f);
            set.setDrawCircleHole(false)
            set.setDrawFilled(true)
            set.setFormLineWidth(1f)
            val data = LineData(set)
            lineChart.data = data
            lineChart.animateX(animationDuration, Easing.EaseOutSine)
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                insightViewModel.showEntryType.value = when (position) {
                    0 -> EntryType.Income
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
        adapter = CategoryLegendAdapter(requireContext())
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
        pieChart.setUsePercentValues(true)
        pieChart.setDrawSlicesUnderHole(false)
        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 90f
        pieChart.description = null
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.rotationAngle = 180.0f
        pieChart.setHoleColor(Color.parseColor("#00000000"))
    }

    private fun setupLineChart() {
        lineChart.description = null

        val formatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return labels.getOrNull(value.toInt()) ?: ""
            }
        }

        val xAxis = lineChart.xAxis
        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter
        lineChart.axisRight.isEnabled = false
    }
}