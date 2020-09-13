package com.hackademy.monetrix.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.app.ActivityCompat

object UIUtil {

    fun Context.getResource(name: String): Drawable? {
        val resID = this.resources.getIdentifier(name, "drawable", this.packageName)
        try {
            return ActivityCompat.getDrawable(this, resID)
        } catch (err: Exception) {

        }
        return null
    }

}