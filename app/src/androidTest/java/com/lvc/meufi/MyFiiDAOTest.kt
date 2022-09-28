package com.lvc.meufi

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lvc.meufi.model.Fii
import com.lvc.meufi.model.FiiDividend
import com.lvc.meufi.model.FiiType
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.model.MyFii
import com.lvc.meufi.persistence.local.FiiDAO
import com.lvc.meufi.persistence.local.FiiDatabase
import com.lvc.meufi.persistence.local.FiiDividendDAO
import com.lvc.meufi.persistence.local.MyFiiDAO
import com.lvc.meufi.utils.toMonthYear
import java.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MyFiiDAOTest {

    companion object {
        private const val FII_CODE_EXAMPLE = "HSAF11"
    }

    private lateinit var database: FiiDatabase

    private lateinit var myFiiDAO: MyFiiDAO
    private lateinit var fiiDividendDAO: FiiDividendDAO
    private lateinit var fiiDAO: FiiDAO

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FiiDatabase::class.java
        ).build()

        myFiiDAO = database.createMyFiiDAO()
        fiiDividendDAO = database.createFiiDividendDAO()
        fiiDAO = database.createFiiDAO()
    }

    @Test
    fun updateFiiAmount() = runTest {
        myFiiDAO.saverOrUpdate(
            MyFii(
                fiiCode = FII_CODE_EXAMPLE,
                amount = 10,
                updatedAt = getPastDate(Calendar.JANUARY).toMonthYear()
            )
        )
        Assert.assertTrue(myFiiDAO.getAll().size == 1)
        myFiiDAO.saverOrUpdate(
            MyFii(
                fiiCode = FII_CODE_EXAMPLE,
                amount = 20,
                updatedAt = getPastDate(Calendar.JANUARY).toMonthYear()
            )
        )
        Assert.assertTrue(myFiiDAO.getAll().size == 1)
        Assert.assertTrue(myFiiDAO.getAll().first().amount == 20)
    }

    @Test
    fun ensureDividendDataWillBeReturned_whenThereIsFiisOnTheWalletAndDividends() = runTest {
        // SAVE A FII
        fiiDAO.saverOrUpdate(
            Fii(
                code = FII_CODE_EXAMPLE,
                name = "HSI Ativos Financeiros",
                type = FiiType.PAPER
            )
        )
        // Add it on the wallet (for two months January and february)
        myFiiDAO.saverOrUpdate(
            MyFii(
                fiiCode = FII_CODE_EXAMPLE,
                amount = 10,
                updatedAt = getPastDate(Calendar.JANUARY).toMonthYear()
            )
        )

        myFiiDAO.saverOrUpdate(
            MyFii(
                fiiCode = FII_CODE_EXAMPLE,
                amount = 20,
                updatedAt = getPastDate(Calendar.FEBRUARY).toMonthYear()
            )
        )

        Assert.assertTrue(myFiiDAO.getAll().size == 2)

        // Save Dividend for January e February
        fiiDividendDAO.saveOrUpdate(
            FiiDividend(
                fiiCode = FII_CODE_EXAMPLE,
                dividend = 1f,
                paymentDate = getPastDate(Calendar.JANUARY).toMonthYear()
            )
        )

        fiiDividendDAO.saveOrUpdate(
            FiiDividend(
                fiiCode = FII_CODE_EXAMPLE,
                dividend = 2f,
                paymentDate = getPastDate(Calendar.FEBRUARY).toMonthYear()
            )
        )

        // Ensure that Fii with dividend will return the correct amount for each month
        val dividendsData = fiiDividendDAO.getFiiWithDividend()
        Assert.assertEquals(2, dividendsData.size)

        dividendsData[0].let {
            Assert.assertEquals(2f, it.dividend)
            Assert.assertEquals(20, it.amount)
            Assert.assertEquals(FII_CODE_EXAMPLE, it.fiiCode)
            Assert.assertEquals(FiiType.PAPER, it.type)
        }

        dividendsData[1].let {
            Assert.assertEquals(1f, it.dividend)
            Assert.assertEquals(10, it.amount)
            Assert.assertEquals(FII_CODE_EXAMPLE, it.fiiCode)
            Assert.assertEquals(FiiType.PAPER, it.type)
        }
    }

    @Test
    fun saveOneFiiInfo() = runTest {
        val myFii = MyFii(
            fiiCode = FII_CODE_EXAMPLE,
            amount = 10
        )
        myFiiDAO.saverOrUpdate(myFii)

        myFiiDAO.getAll().let { fiis ->
            assert(fiis.size == 1)
            assert(fiis.first().fiiCode == FII_CODE_EXAMPLE)
            assert(fiis.first().amount == 10)
        }
    }

    @Test
    fun ensureReturnFiiByDateCorrectly() = runTest {
        val myFii = MyFii(
            fiiCode = FII_CODE_EXAMPLE,
            amount = 10,
            updatedAt = MonthDayYear(1,2022)
        )
        myFiiDAO.saverOrUpdate(myFii)

        myFiiDAO.getSingleFiiFromDate(1, 2022).let { fii ->
            Assert.assertNotNull(fii)
            assert(fii?.fiiCode == FII_CODE_EXAMPLE)
            assert(fii?.amount == 10)
        }
    }

    @Test
    fun ensureReturnFiiNullByUnsavedDate() = runTest {
        val myFii = MyFii(
            fiiCode = FII_CODE_EXAMPLE,
            amount = 10,
            updatedAt = MonthDayYear(1,2022)
        )
        myFiiDAO.saverOrUpdate(myFii)

        myFiiDAO.getSingleFiiFromDate(2, 2022).let { fii ->
            Assert.assertNull(fii)
        }
    }


    @Test
    fun updateFiiOnTheWallet() = runTest {
        // Save an element
        val myFii = MyFii(
            fiiCode = FII_CODE_EXAMPLE,
            amount = 10
        )
        myFiiDAO.saverOrUpdate(myFii)

        myFiiDAO.getAll().let { fiis ->
            assert(fiis.size == 1)
            assert(fiis.first().fiiCode == FII_CODE_EXAMPLE)
            assert(fiis.first().amount == 10)
        }

        // edit an element and ensure its updated for the same date
        myFiiDAO.saverOrUpdate(
            MyFii(
                fiiCode = FII_CODE_EXAMPLE,
                amount = 23
            )
        )

        myFiiDAO.getAll().let { fiis ->
            assert(fiis.size == 1)
            assert(fiis.first().fiiCode == FII_CODE_EXAMPLE)
            assert(fiis.first().amount == 23)
        }
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    private fun getPastDate(month: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return calendar.time
    }
}