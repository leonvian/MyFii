package com.lvc.meufi.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lvc.meufi.home.data.DividendPageData
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.persistence.FiiDividendRepository
import com.lvc.meufi.utils.toJustMonthYear
import com.lvc.meufi.utils.toMonthYearDay
import java.util.Calendar
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fiiDividendRepository: FiiDividendRepository
) : ViewModel() {

    val dividendPage = mutableStateOf(DividendPageData())
    val loading = mutableStateOf(true)
    val selectedMonth = mutableStateOf(
        Calendar.getInstance().toJustMonthYear()
    )
    val months = mutableStateOf( listOf<MonthDayYear>() )

    fun onSelectedMonth(monthDayYear: MonthDayYear) {
        selectedMonth.value = monthDayYear
        loadFiiDividedPage(monthDayYear)
    }

    fun loadFiiDividedPage(monthDayYear: MonthDayYear) {
        viewModelScope.launch {
            loading.value = true

            Log.i("DATA", "SAVE DIVIDENDS")
            fiiDividendRepository.loadFiiDividends()

            Log.i("DATA", "Get Dividends Locally")
            val fiiDividendData = fiiDividendRepository.getFiiOnWalletData(monthDayYear)
            if (fiiDividendData.isNotEmpty()) {
                dividendPage.value = fiiDividendData.toDividendPage()
            } else {
                dividendPage.value = DividendPageData(monthDayYear)
            }

            loading.value = false
        }
    }

    fun loadMonthsToDisplay() {
        viewModelScope.launch {
           months.value = fiiDividendRepository.getDatesThatContainsFiis()
        }
    }

    fun reloadDividendPage() {
        loadFiiDividedPage(selectedMonth.value)
    }

    private fun List<FiiDividendData>.toDividendPage(): DividendPageData {
        val date: MonthDayYear = get(0).monthYear
        var dividendSum = 0f
        forEach {
            dividendSum += it.dividendSum
        }

        return DividendPageData(
            date = date,
            dividendSum = dividendSum,
            dividendDiffByPreviousMonth = 0f,
            dividendData = this
        )
    }

}