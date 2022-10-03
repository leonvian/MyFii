package com.lvc.meufi.persistence.local

import android.content.Context
import java.util.Date

private const val PREF_NAME = "LocalDataRegister"
private const val KNOWN_DATA_SAVED_NAME = "KNOWN_DATA_SAVED"
private const val LAST_TIME_API_UPDATE = "LAST_TIME_API_UPDATE"

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

    fun registerUpdateAPICall() {
        val sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putLong(LAST_TIME_API_UPDATE, Date().time)
        editor.apply()
    }

    fun getLastTimeApiCall(): Long? {
        val sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val result = sharedPreference.getLong(LAST_TIME_API_UPDATE, 0)
        return if (result == 0L) null else result
    }

}