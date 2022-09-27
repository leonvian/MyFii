package com.lvc.meufi.persistence

import com.lvc.meufi.data_scripts.InitialScripManager
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.persistence.local.FiiDAO
import com.lvc.meufi.persistence.local.FiiDividendDAO
import com.lvc.meufi.persistence.local.MyFiiDAO
import com.lvc.meufi.persistence.remote.RawDividendData
import com.lvc.meufi.persistence.remote.SkrapeHandler
import com.lvc.meufi.utils.toMonthYear
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FiiDividendRepository(
    private val myFiiDAO: MyFiiDAO,
    private val fiiDAO: FiiDAO,
    private val fiiDividendDAO: FiiDividendDAO,
    private val scrapData: SkrapeHandler,
    private val scriptManager: InitialScripManager
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    suspend fun getAllDividends() = fiiDividendDAO.getAll()
    suspend fun getAllFiis() = fiiDAO.getAll()
    suspend fun getAllMyFiis() = myFiiDAO.getAll()

    suspend fun getFiiWithDividendLocally(): List<FiiDividendData> {
        val dividendData = withContext(Dispatchers.Default) {
            fiiDividendDAO.getFiiWithDividend()
        }

        return dividendData
    }

    suspend fun loadFiiDividends() {
        withContext(Dispatchers.IO) {
            scriptManager.saveKnownData()

            val today = Date().toMonthYear()
            val fiis = fiiDAO.getNotUpdatedFiis(month = today.month, year = today.year)
            fiis.forEach { fii ->
                scrapData.collectData(
                    fiiCode = fii.code,
                    onDividendsCollected = { rawDividendData ->
                        saveDividends(fii.code, rawDividendData)
                    })
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