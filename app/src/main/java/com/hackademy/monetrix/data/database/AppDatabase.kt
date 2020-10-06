package com.hackademy.monetrix.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hackademy.monetrix.data.dao.CategoryDao
import com.hackademy.monetrix.data.dao.SavingPlanDao
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.model.SavingPlan
import com.hackademy.monetrix.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*

@Database(
    entities = [Entry::class, Category::class, SavingPlan::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun savingPlanDao(): SavingPlanDao



    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val transactionDao = database.transactionDao()
                    val categoryDao = database.categoryDao()
                    val savingPlanDao = database.savingPlanDao()
                    // Delete all content here.
                    transactionDao.deleteAll()
                    categoryDao.deleteAll()
                    savingPlanDao.deleteAll()

                    // Add sample words.
                    val categories = arrayOf(
                        Category(
                            name = "Food & Drink",
                            image = "food"
                        ), Category(
                            name = "Entertainment",
                            image = "ticket"
                        ), Category(
                            name = "Utilities",
                            image = "utilities"
                        ), Category(
                            name = "Other",
                            image = "invoice"
                        ), Category(
                            name = "Mutual Funds",
                            image = "invoice"
                        ), Category(
                            name = "Stocks",
                            image = "invoice"
                        )
                    )
                    val ids: Array<Long> = categoryDao.insertAll(*categories)
                    val month = 30*24*60*60*1000;
                    val entries = arrayOf(
                        Entry(
                            description = "Salary",
                            amount = 100000.0,
                            date = Date(Calendar.getInstance().time.time ),
                            categoryId = ids[3],
                            type = EntryType.Income
                        ), Entry(
                            description = "Burger",
                            amount = 400.0,
                            date = Date(Calendar.getInstance().time.time),
                            categoryId = ids[0],
                            type = EntryType.Expense
                        ), Entry(
                            description = "Movie",
                            amount = 1000.0,
                            date = Date(Calendar.getInstance().time.time),
                            categoryId = ids[1],
                            type = EntryType.Expense
                        ), Entry(
                            description = "Mobile",
                            amount = 20000.0,
                            date = Date(Calendar.getInstance().time.time),
                            categoryId = ids[2],
                            type = EntryType.Expense
                        ),
                        Entry(
                            description = "Light Bill",
                            amount = 5000.0,
                            date = Date(Calendar.getInstance().time.time),
                            categoryId = ids[2],
                            type = EntryType.Expense
                        ), Entry(
                            description = "Reliance",
                            amount = 15000.0,
                            date = Date(Calendar.getInstance().time.time),
                            categoryId = ids[5],
                            type = EntryType.Investment
                        ), Entry(
                            description = "TCS",
                            amount = 25000.0,
                            date = Date(Calendar.getInstance().time.time),
                            categoryId = ids[5],
                            type = EntryType.Investment
                        ), Entry(
                            description = "HDFC",
                            amount = 30000.0,
                            date = Date(Calendar.getInstance().time.time),
                            categoryId = ids[4],
                            type = EntryType.Investment
                        ),
                        Entry(
                            description = "Salary",
                            amount = 90000.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[3],
                            type = EntryType.Income
                        ), Entry(
                            description = "Burger",
                            amount = 700.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[0],
                            type = EntryType.Expense
                        ), Entry(
                            description = "Movie",
                            amount = 500.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[1],
                            type = EntryType.Expense
                        ), Entry(
                            description = "Mobile",
                            amount = 14000.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[2],
                            type = EntryType.Expense
                        ),
                        Entry(
                            description = "Light Bill",
                            amount = 3000.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[2],
                            type = EntryType.Expense
                        ), Entry(
                            description = "Reliance",
                            amount = 18000.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[5],
                            type = EntryType.Investment
                        ), Entry(
                            description = "TCS",
                            amount = 15000.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[5],
                            type = EntryType.Investment
                        ), Entry(
                            description = "HDFC",
                            amount = 20000.0,
                            date = Date(Calendar.getInstance().time.time - month),
                            categoryId = ids[4],
                            type = EntryType.Investment
                        )
                    )
                    transactionDao.insertAll(*entries)
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.YEAR, 2021)
                    val savingPlans = arrayOf(
                        SavingPlan(
                            name = "Car",
                            amount = 500000.0,
                            targetDate = Date(cal.time.time),
                            categoryId = ids[3],
                            percent = 40,
                            date = Date(Calendar.getInstance().time.time),
                            completed = false
                        ), SavingPlan(
                            name = "Mobile",
                            amount = 20000.0,
                            targetDate = Date(cal.time.time),
                            categoryId = ids[3],
                            percent = 20,
                            date = Date(Calendar.getInstance().time.time),
                            completed = true
                        ), SavingPlan(
                            name = "Emergency Savings",
                            amount = 10000.0,
                            targetDate = Date(cal.time.time),
                            categoryId = ids[3],
                            percent = 10,
                            date = Date(Calendar.getInstance().time.time),
                            completed = false
                        )
                    )
                    savingPlanDao.insertAll(*savingPlans)
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