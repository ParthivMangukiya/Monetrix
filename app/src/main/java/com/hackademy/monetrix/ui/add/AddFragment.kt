package com.hackademy.monetrix.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.databinding.FragmentAddBinding
import com.hackademy.monetrix.ui.adapter.CategoryAdapter
import com.hackademy.monetrix.ui.adapter.TransactionAdapter
import java.util.*

class AddFragment : Fragment() {

    private lateinit var addViewModel: AddViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_add, container, false)
        val view:View = binding.root
        binding.lifecycleOwner = this
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        binding.viewmodel = addViewModel

        val adapter = activity?.applicationContext?.let { CategoryAdapter(it) }
        val recyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context?.applicationContext, 4)
        addViewModel.getCategories().observe(viewLifecycleOwner, Observer { categories ->
            // Update the cached copy of the words in the adapter.
            categories?.let { adapter?.setCategories(it) }
        })
        return view
    }
}