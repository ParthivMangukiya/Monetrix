package com.hackademy.monetrix.ui.add

import android.animation.LayoutTransition
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackademy.monetrix.R
import com.hackademy.monetrix.databinding.FragmentAddBinding
import com.hackademy.monetrix.ui.adapter.CategoryAdapter
import java.util.*


class AddFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        const val TAG = "AddFragment"
    }

    private lateinit var addViewModel: AddViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        val binding: FragmentAddBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        val view: View = binding.root
        binding.lifecycleOwner = this
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        binding.viewmodel = addViewModel

        view.findViewById<LinearLayout>(R.id.fragment_add_container).layoutTransition.enableTransitionType(
            LayoutTransition.CHANGING
        )
        val adapter = CategoryAdapter(requireActivity()) { id -> addViewModel.onClick(id) }
        val recyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context?.applicationContext, 3)
        addViewModel.getCategories().observe(viewLifecycleOwner, Observer { categories ->
            // Update the cached copy of the words in the adapter.
            categories?.let { adapter.setCategories(it) }
            startPostponedEnterTransition()
            recyclerView.scheduleLayoutAnimation()
        })
        addViewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Transaction Saved", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })
        addViewModel.pickupDate.observe(viewLifecycleOwner, Observer {
            if (it) {
                val date = addViewModel.date.value
                val cal = Calendar.getInstance()

                date?.let {
                    cal.timeInMillis = date.time
                    val datePickerDialog =
                        DatePickerDialog(
                            requireContext(), this,
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        )
                    datePickerDialog.show()
                }
            }
        })
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val cal = Calendar.getInstance()
        cal.set(year, month, day)
        addViewModel._date.value = Date(cal.time.time)
        addViewModel.pickupDate.value = false
    }
}