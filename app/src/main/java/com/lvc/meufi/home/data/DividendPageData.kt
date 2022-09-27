package com.lvc.meufi.home.data

import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.model.round2Places

data class DividendPageData(
    val date: MonthDayYear,
    val dividendSum: Float,
    val dividendData: List<FiiDividendData>,
    val dividendDiffByPreviousMonth: Float,
) {
    fun dividendSumToDisplay(): String =
        dividendSum.round2Places()

}