package com.ms.to_dolistapp.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ms.to_dolistapp.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * @AUTHOR: Mehedi Hasan
 * @DATE: 2/3/2021, Wed
 */
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("database_logger %s", "created")

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task(name = "Go for lunch", completed = true))
                dao.insert(Task(name = "Attend Meeting"))
                dao.insert(Task(name = "Reply Message"))
                dao.insert(Task(name = "Visit Dhaka"))
                dao.insert(Task(name = "Call Mom", completed = true))
                dao.insert(Task(name = "Buy Groceries", important = true))
                dao.insert(Task(name = "Go for Gym", important = true))
                dao.insert(Task(name = "Have Dinner", important = true))
            }
        }
    }
}