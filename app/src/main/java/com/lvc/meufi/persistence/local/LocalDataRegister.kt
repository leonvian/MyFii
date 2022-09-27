package com.lvc.meufi.persistence.local

import android.content.Context
import android.content.SharedPreferences

private const val PREF_NAME = "LocalDataRegister"
private const val KNOWN_DATA_SAVED_NAME = "KNOWN_DATA_SAVED"

class LocalDataRegister(private val context: Context) {

    fun isKnownDataSaved(): Boolean {
        val sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(KNOWN_DATA_SAVED_NAME, false)
    }

    fun markKnownDataStored() {
        val sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean(KNOWN_DATA_SAVED_NAME, true)
        editor.apply()
    }

}