package com.hackademy.monetrix.ui.transaction

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
import com.hackademy.monetrix.ui.adapter.TransactionAdapter

class TransactionsFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_transactions, container, false)
        val tabLayout: TabLayout = root.findViewById(R.id.transactionsTabLayout)
        val recyclerView = root.findViewById<RecyclerView>(R.id.transactionsRecyclerView)
        val adapter = activity?.applicationContext?.let { TransactionAdapter(it) }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context?.applicationContext)

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        transactionViewModel.transactions.observe(viewLifecycleOwner, Observer { transactions ->
            // Update the cached copy of the words in the adapter.
            transactions?.let { adapter?.setTransactions(it) }
            recyclerView.scheduleLayoutAnimation()
        })

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                transactionViewModel.showIncome.value = (position == 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return root
    }
}