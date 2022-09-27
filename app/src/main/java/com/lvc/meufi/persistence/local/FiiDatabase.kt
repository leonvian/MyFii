package com.lvc.meufi.persistence.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lvc.meufi.model.Buy
import com.lvc.meufi.model.Fii
import com.lvc.meufi.model.FiiDividend
import com.lvc.meufi.model.MyFii

@Database(
    entities = [
        Fii::class,
        FiiDividend::class,
        Buy::class,
        MyFii::class
    ], version = 1
)
@TypeConverters(RoomTypeConverters::class)
abstract class FiiDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "fii"

        fun createDatabase(context: Context): FiiDatabase {
            return Room.databaseBuilder(
                context,
                FiiDatabase::class.java, DATABASE_NAME
            ).build()
        }

    }

    abstract fun createFiiDAO(): FiiDAO

    abstract fun createMyFiiDAO(): MyFiiDAO

    abstract fun createFiiDividendDAO(): FiiDividendDAO

}