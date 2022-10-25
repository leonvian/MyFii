package com.lvc.meufi

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lvc.meufi.model.Fii
import com.lvc.meufi.model.FiiDividend
import com.lvc.meufi.model.FiiType
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.persistence.local.FiiDAO
import com.lvc.meufi.persistence.local.FiiDatabase
import com.lvc.meufi.persistence.local.FiiDividendDAO
import com.lvc.meufi.persistence.local.MyFiiDAO
import com.lvc.meufi.utils.toDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class FiiDAOTest {

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
    fun checkGetFiiWithNoDividends() = runTest {
        // Save 4 FIIs
        val fiiList = mutableListOf<Fii>()
        fiiList += Fii(
            code = "CPTS11",
            name = "Capitania Securities",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "DEVA11",
            name = "Devant Recebiveis Imobiliarios",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "HCTR11",
            name = "Fundo DE Investimento Imobiliario Hectare",
            type = FiiType.PAPER
        )
        fiiList += Fii(
            code = "HGRU11",
            name = "SHG Renda Urbana-FI Imobiliario",
            type = FiiType.BRICK
        )
        fiiDAO.saverOrUpdate(fiiList)

        // Save Dividends for 2 fiis
        fiiDividendDAO.saveOrUpdate(
            FiiDividend(
                fiiCode = "HGRU11",
                dividend = 0.54f,
                baseDate = MonthDayYear(1, 2022).toDate(),
                paymentDate = MonthDayYear(1, 2022),
            )
        )
        fiiDividendDAO.saveOrUpdate(
            FiiDividend(
                fiiCode = "HCTR11",
                dividend = 1.54f,
                baseDate = MonthDayYear(1, 2022).toDate(),
                paymentDate = MonthDayYear(1, 2022),
            )
        )

        // Check if its going to re
       val notUpdateFiis = fiiDAO.getNotUpdatedFiis(1, 2022)
        Assert.assertEquals(2, notUpdateFiis.size)
        Assert.assertNotNull(notUpdateFiis.find { it.code ==  "CPTS11" })
        Assert.assertNotNull(notUpdateFiis.find { it.code ==  "DEVA11" })
    }
}