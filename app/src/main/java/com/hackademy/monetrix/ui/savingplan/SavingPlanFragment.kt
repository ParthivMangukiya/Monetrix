package com.hackademy.monetrix.ui.savingplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.hackademy.monetrix.R
import com.hackademy.monetrix.ui.adapter.SavingPlanAdapter

class SavingPlanFragment : Fragment() {

    private lateinit var savingPlanViewModel: SavingPlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_savingplans, container, false)
        val tabLayout: TabLayout = root.findViewById(R.id.savingPlansTabLayout)
        val recyclerView = root.findViewById<RecyclerView>(R.id.savingPlansRecyclerView)
        val adapter = activity?.applicationContext?.let { SavingPlanAdapter(it) }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context?.applicationContext)

        savingPlanViewModel = ViewModelProvider(this).get(SavingPlanViewModel::class.java)
        savingPlanViewModel.savingPlans.observe(viewLifecycleOwner, Observer { savingPlans ->
            // Update the cached copy of the words in the adapter.
            savingPlans?.let { adapter?.setSavingPlans(it) }
        })

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                savingPlanViewModel.showCurrent.value = (position == 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return root
    }
}