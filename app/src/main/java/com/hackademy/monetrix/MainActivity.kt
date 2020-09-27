package com.hackademy.monetrix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hackademy.monetrix.ui.add.AddFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = null
        title = null
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        val addButton: FloatingActionButton = findViewById(R.id.addAction)
        addButton.setOnClickListener {
            val dialog = AddFragment()
            dialog.show(supportFragmentManager, AddFragment.TAG)
        }

        val mDrawableImage = resources.getDrawable(R.drawable.background, theme)
        mDrawableImage.alpha = 120
        findViewById<ConstraintLayout>(R.id.container).background = mDrawableImage
    }
}