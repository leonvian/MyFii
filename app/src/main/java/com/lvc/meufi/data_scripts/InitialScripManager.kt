package com.lvc.meufi.data_scripts

import com.lvc.meufi.persistence.local.FiiDAO
import com.lvc.meufi.persistence.local.LocalDataRegister
import com.lvc.meufi.persistence.local.MyFiiDAO

/**
 * Class dedicated to save already know data.
 * Like known fiis and fiis dividends
 */
class InitialScripManager(
    private val fiiDao: FiiDAO,
    private val myFiiDAO: MyFiiDAO,
    private val localDataRegister: LocalDataRegister
) {

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