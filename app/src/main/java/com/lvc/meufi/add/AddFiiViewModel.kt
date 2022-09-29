package com.lvc.meufi.add

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.MyFii
import com.lvc.meufi.persistence.local.MyFiiDAO
import kotlinx.coroutines.launch

/**
 * Adds new Fiis on the wallet
 */
class AddFiiViewModel(
    private val myFiiDAO: MyFiiDAO
) : ViewModel() {

    var fiiCode = mutableStateOf("")
    var fiiAmount = mutableStateOf(0)
    var isEditModeOn = mutableStateOf(false)

    fun applyEntryFii() {
        isEditModeOn.value = false
        onFiiCode("")
        onFiiAmount(0)
    }

    fun applyFiiToEdit(fiiDividendData: FiiDividendData) {
        isEditModeOn.value = true
        onFiiCode(fiiDividendData.fiiCode)
        onFiiAmount(fiiDividendData.amount)
    }

    fun onFiiCode(fiiCode: String) {
        this.fiiCode.value = fiiCode
    }

    fun onFiiAmount(amount: Int) {
        this.fiiAmount.value = amount
    }

    fun onSaveClicked() {
        val myFii = MyFii(
            fiiCode = fiiCode.value,
            amount = fiiAmount.value
        )
        viewModelScope.launch {
            myFiiDAO.saverOrUpdate(myFii)
        }
    }

}