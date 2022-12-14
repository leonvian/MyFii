package com.lvc.meufi.persistence.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lvc.meufi.model.Fii
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.model.MyFii

@Dao
interface MyFiiDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverOrUpdate(myFii: MyFii)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverOrUpdate(myFiis: List<MyFii>)

    @Query("SELECT * FROM MyFii WHERE month = :month AND year = :year")
    suspend fun getFiiFromDate(month: Int, year: Int): List<MyFii>

    @Query("SELECT * FROM MyFii")
    suspend fun getAll(): List<MyFii>

    @Query("SELECT * FROM MyFii WHERE month = :month AND year = :year LIMIT 1")
    suspend fun getSingleFiiFromDate(month: Int, year: Int): MyFii?

    @Query("SELECT * FROM MyFii WHERE month = :month AND year = :year AND fiiCode  NOT IN (SELECT FiiDividend.fiiCode FROM FiiDividend WHERE month = :month AND year = :year)")
    suspend fun getFiisOnWalletWithNoDividend(month: Int, year: Int) : List<MyFii>

    @Query("SELECT DISTINCT month, year, day FROM MyFii")
    suspend fun getMyFiisDates(): List<MonthDayYear>
}
