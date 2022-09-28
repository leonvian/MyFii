package com.lvc.meufi

import android.icu.util.Calendar
import androidx.compose.ui.test.createTestContext
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.lvc.meufi.data_scripts.ScripManager
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.model.MyFii
import com.lvc.meufi.persistence.local.FiiDAO
import com.lvc.meufi.persistence.local.FiiDatabase
import com.lvc.meufi.persistence.local.FiiDividendDAO
import com.lvc.meufi.persistence.local.LocalDataRegister
import com.lvc.meufi.persistence.local.MyFiiDAO
import com.lvc.meufi.utils.toMonthYear
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScriptManagerTests {
    private lateinit var database: FiiDatabase

    private lateinit var myFiiDAO: MyFiiDAO
    private lateinit var fiiDividendDAO: FiiDividendDAO
    private lateinit var fiiDAO: FiiDAO
    private lateinit var scripManager: ScripManager

    companion object {
        private const val FII_CODE_EXAMPLE = "HSAF11"
    }

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FiiDatabase::class.java
        ).build()

        myFiiDAO = database.createMyFiiDAO()
        fiiDividendDAO = database.createFiiDividendDAO()
        fiiDAO = database.createFiiDAO()

        scripManager = ScripManager(
            fiiDAO,
            myFiiDAO,
            LocalDataRegister(ApplicationProvider.getApplicationContext())
        )
    }

    @Test
    fun testUpdateWalletFiisScript() = runTest {
        //Produce and store last month fiis
        val thisMonth = Calendar.getInstance().time.toMonthYear()

        val calendarLastMonth = Calendar.getInstance()
        calendarLastMonth.add(Calendar.MONTH, -1)

        val myFii = MyFii(
            fiiCode = FII_CODE_EXAMPLE,
            amount = 10,
            updatedAt = calendarLastMonth.time.toMonthYear()
        )
        myFiiDAO.saverOrUpdate(myFii)

        //Ensure there is NO fii for the current month BEFORE the script
        myFiiDAO.getFiiFromDate(thisMonth.month, thisMonth.year).let { myFiisForToday ->
            assert(myFiisForToday.isEmpty())
        }

        assert( myFiiDAO.getAll().size == 1)

        //Run the scripts that update the fiis for the current month
        scripManager.updateWalletFiis()

        //Ensure there is fiss for this month after script
        myFiiDAO.getFiiFromDate(thisMonth.month, thisMonth.year).let { myFiisForToday ->
            assert(myFiisForToday.isNotEmpty())
            Assert.assertEquals(FII_CODE_EXAMPLE, myFiisForToday.first().fiiCode)
            Assert.assertEquals(10, myFiisForToday.first().amount)
        }

        assert( myFiiDAO.getAll().size == 2)

    }
}