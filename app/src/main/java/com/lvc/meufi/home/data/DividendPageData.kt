package com.lvc.meufi.home.data

import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.model.round2Places
import com.lvc.meufi.utils.toMonthYearDay
import java.util.Date

data class DividendPageData(
    val date: MonthDayYear,
    val dividendSum: Float,
    val dividendData: List<FiiDividendData>,
    val dividendDiffByPreviousMonth: Float,
) {
    constructor(date: MonthDayYear = Date().toMonthYearDay()) : this(
        date = date,
        dividendSum = 0f,
        dividendData = emptyList(),
        dividendDiffByPreviousMonth = 0f
    )

    fun dividendSumToDisplay(): String =
        dividendSum.round2Places()

}