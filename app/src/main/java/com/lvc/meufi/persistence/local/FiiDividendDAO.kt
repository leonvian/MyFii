package com.lvc.meufi.persistence.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lvc.meufi.model.FiiDividend
import com.lvc.meufi.model.FiiDividendData
import java.time.Month

@Dao
interface FiiDividendDAO {

    @Insert(onConflict = REPLACE)
    suspend fun saveOrUpdate(fiiDividend: FiiDividend)

    @Insert(onConflict = REPLACE)
    suspend fun saveOrUpdate(fiiDividendData: List<FiiDividend>)

    @Query(
        "SELECT MyFii.fiiCode, MyFii.amount, FiiDividend.month, FiiDividend.year, FiiDividend.day, FiiDividend.dividend, Fii.type FROM FiiDividend INNER JOIN Fii ON Fii.code = FiiDividend.fiiCode INNER JOIN MyFii ON MyFii.fiiCode = FiiDividend.fiiCode AND MyFii.month = FiiDividend.month AND MyFii.year = FiiDividend.year ORDER BY FiiDividend.year, FiiDividend.month DESC"
    )
    suspend fun getFiiWithDividend(): List<FiiDividendData>

    @Query("SELECT * FROM FiiDividend")
    suspend fun getAll(): List<FiiDividend>


}