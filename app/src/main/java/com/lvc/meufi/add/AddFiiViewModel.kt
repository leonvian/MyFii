package com.lvc.meufi.add

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * Adds new Fiis on the wallet
 */
class AddFiiViewModel : ViewModel() {

    var fiiCode = mutableStateOf("")
    var fiiAmount = mutableStateOf(0)

    fun onFiiCode(fiiCode: String) {
        this.fiiCode.value = fiiCode
    }

    fun onFiiAmount(amount: Int) {
        this.fiiAmount.value = amount
    }

    fun onSaveClicked() {
        Log.i("DATA", "CLICKEDD!!")
    }

}