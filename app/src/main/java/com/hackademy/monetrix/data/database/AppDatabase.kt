package com.hackademy.monetrix.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hackademy.monetrix.data.dao.CategoryDao
import com.hackademy.monetrix.data.dao.EntryDao
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*

@Database(entities = [Entry::class, Category::class],
    version = 2,
    exportSchema = false)
@TypeConverters(Converters::class)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun entryDao(): EntryDao
    abstract fun categoryDao(): CategoryDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val entryDao = database.entryDao()
                    val categoryDao = database.categoryDao()
                    // Delete all content here.
                    entryDao.deleteAll()
                    categoryDao.deleteAll()

                    // Add sample words.
                    val category = Category(
                        name = "Food"
                    )
                    val ids: Array<Long> = categoryDao.insertAll(category)
                    val entry = Entry(
                        amount = 10.5,
                        date = Date(Calendar.getInstance().time.time),
                        categoryId = ids[0],
                        type = EntryType.Income
                    )
                    entryDao.insertAll(entry)

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
           return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "monetrix_database"
                )
                .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}