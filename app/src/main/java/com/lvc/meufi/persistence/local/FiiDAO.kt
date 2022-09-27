package com.lvc.meufi.persistence.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lvc.meufi.model.Fii

@Dao
interface FiiDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverOrUpdate(fii: Fii)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverOrUpdate(fii: List<Fii>)

    @Query("SELECT * FROM Fii WHERE code NOT IN (SELECT FiiDividend.fiiCode FROM FiiDividend WHERE month = :month AND year = :year)")
    suspend fun getNotUpdatedFiis(month: Int, year: Int) : List<Fii>

    @Query("SELECT * FROM Fii")
    suspend fun getAll() : List<Fii>
}