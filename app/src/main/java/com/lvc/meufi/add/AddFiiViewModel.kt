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
    var fiiAmount = mutableStateOf("")
    var isEditModeOn = mutableStateOf(false)

    fun applyEntryFii() {
        isEditModeOn.value = false
        onFiiCode("")
        onFiiAmount("")
    }

    fun applyFiiToEdit(fiiDividendData: FiiDividendData) {
        isEditModeOn.value = true
        onFiiCode(fiiDividendData.fiiCode)
        onFiiAmount(fiiDividendData.amount.toString())
    }

    fun onFiiCode(fiiCode: String) {
        this.fiiCode.value = fiiCode
    }

    fun onFiiAmount(amount: String) {
        this.fiiAmount.value = amount
    }

    fun onSaveClicked() {
        val myFii = MyFii(
            fiiCode = fiiCode.value,
            amount = fiiAmount.value.toIntSafely()
        )
        viewModelScope.launch {
            myFiiDAO.saverOrUpdate(myFii)
        }
    }

    private fun String.toIntSafely(): Int {
        return try {
            this.toInt()
        } catch (e: java.lang.Exception) {
            0
        }
    }

}