package com.hackademy.monetrix.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.lzyzsd.circleprogress.ArcProgress
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.EntryTotal
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.ui.adapter.CategoryTotalAdapter
import com.hackademy.monetrix.ui.adapter.EntryTotalAdapter
import com.hackademy.monetrix.util.Constants
import com.hackademy.monetrix.util.GridSpacingItemDecorator
import com.hackademy.monetrix.util.Util.toRupee

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var progressBar: ArcProgress
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryTotalAdapter
    private lateinit var budgetTextView: TextView
    private lateinit var expenseTextView: TextView
    private lateinit var balanceTextView: TextView
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
        progressBar = root.findViewById(R.id.arc_progress)
        recyclerView = root.findViewById(R.id.category_total_recyclerview)
        budgetTextView = root.findViewById(R.id.budget)
        expenseTextView = root.findViewById(R.id.expense)
        balanceTextView = root.findViewById(R.id.balance)
        animationDuration = requireContext().resources.getInteger(R.integer.anim_duration_medium)
        setupRecyclerView()

        progressBar.bottomText = "Budget"
        progressBar.finishedStrokeColor = ContextCompat.getColor(requireContext(), R.color.chart_color9)
        progressBar.unfinishedStrokeColor = ContextCompat.getColor(requireContext(), R.color.chart_color12)

        homeViewModel.categoriesTotal.observe(viewLifecycleOwner, Observer { list ->
            adapter.setCategories(list)
            recyclerView.scheduleLayoutAnimation()
        })

        homeViewModel.entryTotal.observe(viewLifecycleOwner, Observer { list ->
            val expense = getAmount(list,EntryType.Expense)
            val budget = 30000f.toDouble()
            val balance =  budget - expense
            balanceTextView.text = balance.toRupee()
            expenseTextView.text = expense.toRupee()
            budgetTextView.text = budget.toRupee()
            var progress = (expense/budget*100).toInt()
            if(progress > 100) {
                progress = 100
            }
            progressBar.progress = progress
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                homeViewModel.showEntryType.value = when(position) {
                    0  -> EntryType.Expense
                    1 -> EntryType.Investment
                    else -> EntryType.Expense
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return root
    }

    private fun getAmount(list: List<EntryTotal>, type: EntryType): Double {
        for(e in list) {
            if(e.type == type) {
                return e.total
            }
        }
        return 0f.toDouble()
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

}