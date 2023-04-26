package com.example.testtask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testtask.data.remote.NumberDto

@Database(
    entities = [NumberDto::class],
    version = 1
)

abstract class NumbersDB : RoomDatabase() {

    abstract fun getNumbersDao(): NumbersDao

    companion object {
        @Volatile
        private var instance: NumbersDB? = null

        operator fun invoke(context: Context): NumbersDB {
            return instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, NumbersDB::class.java, "saved_numbers_db.db"
        ).fallbackToDestructiveMigration().build()

    }

}
