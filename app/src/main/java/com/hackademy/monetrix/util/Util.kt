package com.hackademy.monetrix.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java.text.SimpleDateFormat
import java.util.*

object Util {

    fun Context.getResource(name: String): Drawable? {
        val resID = this.resources.getIdentifier(name, "drawable", this.packageName)
        try {
            return ActivityCompat.getDrawable(this, resID)
        } catch (err: Exception) {

        }
        return null
    }

    fun Date.getFormatted(pattern: String): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(Date())
    }

    fun <T, A, B> LiveData<A>.combineAndCompute(
        other: LiveData<B>,
        onChange: (A, B) -> T
    ): MediatorLiveData<T> {

        var source1emitted = false
        var source2emitted = false

        val result = MediatorLiveData<T>()

        val mergeF = {
            val source1Value = this.value
            val source2Value = other.value

            if (source1emitted && source2emitted) {
                result.value = onChange.invoke(source1Value!!, source2Value!!)
            }
        }

        result.addSource(this) { source1emitted = true; mergeF.invoke() }
        result.addSource(other) { source2emitted = true; mergeF.invoke() }

        return result
    }

    fun Double.toRupee(): String {
        return "₹ $this"
    }

}