package com.hackademy.monetrix

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hackademy.monetrix.util.Util.toRupee


class BalanceActivity : AppCompatActivity() {

    private lateinit var predictedBudget: TextView
    private lateinit var budgetTitle: TextView
    private lateinit var budgetEditText: EditText
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)
        predictedBudget = findViewById(R.id.predictedBudget)
        budgetEditText = findViewById(R.id.budgetEditText)
        budgetTitle = findViewById(R.id.budgetTitle)
        nextButton = findViewById(R.id.nextButton)
        predictedBudget.text = (0.0).toRupee()
        budgetEditText.setText("0")
        budgetEditText.visibility = View.INVISIBLE
        budgetTitle.visibility = View.INVISIBLE
        nextButton.setOnClickListener {
            navigateToHome()
        }
    }

    override fun onStart() {
        super.onStart()
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Your Income")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.cancel()
            budgetEditText.visibility = View.VISIBLE
            budgetTitle.visibility = View.VISIBLE
            setBudget(input.text.toString().toDouble())
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
            navigateToHome()
        }
        builder.show()

    }

    private fun setBudget(income: Double) {
        val budget = income*0.4
        predictedBudget.text = budget.toRupee()
        budgetEditText.setText(budget.toString())
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}