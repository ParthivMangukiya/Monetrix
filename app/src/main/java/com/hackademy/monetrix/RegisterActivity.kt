package com.hackademy.monetrix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val registerButton: Button = findViewById(R.id.login)

        registerButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val mDrawableImage = resources.getDrawable(R.drawable.background, theme)
        mDrawableImage.alpha = 200
        findViewById<ConstraintLayout>(R.id.container).background = mDrawableImage
    }
}