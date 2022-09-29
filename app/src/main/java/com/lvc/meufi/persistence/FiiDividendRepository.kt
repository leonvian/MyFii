package com.lvc.meufi.persistence

import com.lvc.meufi.data_scripts.ScripManager
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.persistence.local.FiiDAO
import com.lvc.meufi.persistence.local.FiiDividendDAO
import com.lvc.meufi.persistence.local.MyFiiDAO
import com.lvc.meufi.persistence.remote.RawDividendData
import com.lvc.meufi.persistence.remote.SkrapeHandler
import com.lvc.meufi.utils.toMonthYearDay
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FiiDividendRepository(
    private val fiiDAO: FiiDAO,
    private val fiiDividendDAO: FiiDividendDAO,
    private val myFiiDAO: MyFiiDAO,
    private val scrapData: SkrapeHandler,
    private val scriptManager: ScripManager
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    suspend fun getDatesThatContainsFiis(): List<MonthDayYear> {
        return withContext(Dispatchers.Default) {
            myFiiDAO.getMyFiisDates()
        }
    }

    suspend fun getFiiOnWalletData(monthDayYear: MonthDayYear): List<FiiDividendData> {
        val dividendData = arrayListOf<FiiDividendData>()
        withContext(Dispatchers.Default) {
            dividendData += fiiDividendDAO.getFiiWithDividend(monthDayYear.month, monthDayYear.year)
            dividendData += myFiiDAO.getFiisOnWalletWithNoDividend(
                monthDayYear.month,
                monthDayYear.year
            ).map {
                it.toFiiDividendData()
            }
        }

        return dividendData
    }

    suspend fun loadFiiDividends() {
        withContext(Dispatchers.IO) {
            scriptManager.saveKnownData()
            scriptManager.updateWalletFiis()

            val today = Date().toMonthYearDay()
            val fiis = fiiDAO.getNotUpdatedFiis(month = today.month, year = today.year)
            fiis.forEach { fii ->
                scrapData.collectData(
                    fiiCode = fii.code,
                    onDividendsCollected = { rawDividendData ->
                        saveDividends(fii.code, rawDividendData)
                    }
                )
            }
        }
    }

    private fun saveDividends(fii: String, rawDividendData: List<RawDividendData>) {
        scope.launch {
            val dividends = rawDividendData.map { it.toDividendData(fii) }
            fiiDividendDAO.saveOrUpdate(dividends)
        }
    }
}