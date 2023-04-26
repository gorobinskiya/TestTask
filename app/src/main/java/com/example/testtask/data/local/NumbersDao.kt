package com.example.testtask.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.data.remote.NumberDto

@Dao
interface NumbersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(numberData: NumberDto): Long


    @Query("SELECT DISTINCT * FROM numbers ORDER BY id DESC")
    fun getAllNumbersDataLiveData(): LiveData<List<NumberDto>>
}
