package com.hackademy.monetrix.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anychart.anychart.*
import com.hackademy.monetrix.R
import com.hackademy.monetrix.ui.transaction.TransactionViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var anyChartView: AnyChartView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        anyChartView = root.findViewById<AnyChartView>(R.id.any_chart_view)

        homeViewModel.expenseByCategories.observe(viewLifecycleOwner, Observer {
            val pie: Pie = AnyChart.pie()
            val data: List<DataEntry> = it.map {
                ValueDataEntry(it.category.name, it.total)
            }
            pie.data(data)
            anyChartView.setChart(pie)
        })
        return root
    }
}