package com.hackademy.monetrix.util

import androidx.room.Entity
import androidx.room.TypeConverter
import com.hackademy.monetrix.data.model.EntryType
import java.time.Instant
import java.util.*

class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromDate(value: Date): Long {
            return value.time
        }

        @TypeConverter
        @JvmStatic
        fun toDate(value: Long): Date {
            return Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun fromType(value: EntryType): Int {
            return value.code
        }

        @TypeConverter
        @JvmStatic
        fun toType(value: Int): EntryType {
            return when(value) {
                0 -> EntryType.Income
                1 -> EntryType.Expense
                2 -> EntryType.Investment
                else -> {
                 EntryType.Income
                }
            }
        }
    }
}