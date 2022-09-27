package com.lvc.meufi.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lvc.meufi.home.data.DividendPageData
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.persistence.FiiDividendRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fiiDividendRepository: FiiDividendRepository
) : ViewModel() {

    val dividendPages = mutableStateOf(arrayListOf<DividendPageData>())
    val loading = mutableStateOf(true)

    fun loadFiiDividedPage() {
        viewModelScope.launch {
            loading.value = true

            Log.i("DATA", "SAVE DIVIDENDS")
            fiiDividendRepository.loadFiiDividends()

            Log.i("DATA", "Get Dividends Locally")
            val listDividendPage = arrayListOf<DividendPageData>()
            val fiiDividendData = fiiDividendRepository.getFiiWithDividendLocally()
            var dividendsFromPastMonth = 0f
            fiiDividendData.getMonthsThatContainsDividends().forEach {
                val dividendPageData = fiiDividendData
                    .filterByMonth(it)
                    .toDividendPage(dividendsFromPastMonth)

                listDividendPage.add(dividendPageData)
                dividendsFromPastMonth = dividendPageData.dividendSum
            }
            dividendPages.value = listDividendPage
            loading.value = false
        }
    }

    private fun List<FiiDividendData>.getMonthsThatContainsDividends() =
        this.map {
            it.monthYear
        }.sortedBy {
            it
        }.distinctBy {
            Pair(it.year, it.month)
        }

    private fun List<FiiDividendData>.filterByMonth(targetMonthYear: MonthDayYear) =
        this.filter {
            it.monthYear.month == targetMonthYear.month &&
                    it.monthYear.year == targetMonthYear.year
        }

    private fun List<FiiDividendData>.toDividendPage(previousMonthDividends: Float): DividendPageData {
        val date: MonthDayYear = get(0).monthYear
        var dividendSum = 0f
        forEach {
            dividendSum += it.dividendSum
        }
        val dividendDiffByPreviousMonth = if (previousMonthDividends == 0f) {
            0f
        } else {
            dividendSum - previousMonthDividends
        }

        return DividendPageData(
            date = date,
            dividendSum = dividendSum,
            dividendDiffByPreviousMonth = dividendDiffByPreviousMonth,
            dividendData = this
        )
    }

}