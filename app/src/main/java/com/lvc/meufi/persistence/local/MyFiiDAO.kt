package com.lvc.meufi.persistence.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lvc.meufi.model.MyFii

@Dao
interface MyFiiDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverOrUpdate(myFii: MyFii)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverOrUpdate(myFiis: List<MyFii>)

    @Query("SELECT * FROM MyFii WHERE month = :month AND year = :year  LIMIT 1")
    suspend fun getFirstFromDate(month: Int, year: Int): MyFii

    @Query("SELECT * FROM MyFii")
    suspend fun getAll(): List<MyFii>

}
