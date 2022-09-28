package com.lvc.meufi.data_scripts

import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.persistence.local.FiiDAO
import com.lvc.meufi.persistence.local.LocalDataRegister
import com.lvc.meufi.persistence.local.MyFiiDAO
import com.lvc.meufi.utils.toMonthYear
import java.time.Month
import java.util.Calendar
import java.util.Date

/**
 * Class dedicated to save already know data.
 * Like known fiis and fiis dividends
 */
class ScripManager(
    private val fiiDao: FiiDAO,
    private val myFiiDAO: MyFiiDAO,
    private val localDataRegister: LocalDataRegister
) {

    suspend fun updateWalletFiis() {
        // If theres is no fiis on the wallet for the current month
        if (myFiiDAO.fiisNotUpdatedForCurrentMonth()) {
            val calendar = Calendar.getInstance()
            // get fiis from last month and update them with the current month
            calendar.add(Calendar.MONTH, -1)
            val lastMonth = calendar.time.toMonthYear()
            val updatedFiis = myFiiDAO.getFiiFromDate(lastMonth.month, lastMonth.year).map {
                it.copy(updatedAt = Date().toMonthYear())
            }
            myFiiDAO.saverOrUpdate(updatedFiis)
        }
    }

    private suspend fun MyFiiDAO.fiisNotUpdatedForCurrentMonth(): Boolean {
        val calendar = Calendar.getInstance()
        val thisMonth = calendar.time.toMonthYear()
        return getSingleFiiFromDate(thisMonth.month, thisMonth.year) == null
    }

    suspend fun saveKnownData() {
        if (localDataRegister.isKnownDataSaved())
            return

        saveMyfiis()
        saveKnownFiis()
    }

    private suspend fun saveMyfiis() {
        val myFiis = KnownData.createMyFiis()
        myFiiDAO.saverOrUpdate(myFiis)
    }

    private suspend fun saveKnownFiis() {
        val fiis = KnownData.createListOfFiis()
        fiiDao.saverOrUpdate(fiis)
        localDataRegister.markKnownDataStored()
    }

}