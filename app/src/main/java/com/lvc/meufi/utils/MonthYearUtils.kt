package com.lvc.meufi.utils

import com.lvc.meufi.model.MonthDayYear
import java.time.Month
import java.time.format.TextStyle
import java.util.*

fun MonthDayYear.toDateString(): String {
    val monthStr = toMonth()
    val yearStr = year.toString()
    return if (year == Calendar.getInstance().get(Calendar.YEAR)) {
        monthStr
    } else {
        "$monthStr - ($yearStr)"
    }
}

fun MonthDayYear.toDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.DAY_OF_MONTH, day)

    return calendar.time
}

fun MonthDayYear.toMonth(): String =
    Month.of(month+ 1).getDisplayName(TextStyle.FULL, Locale.getDefault())

fun Date.toMonthYearDay(): MonthDayYear {
    val calendar = Calendar.getInstance()
    calendar.time = this

    return MonthDayYear(
        month = calendar.get(Calendar.MONTH),
        year = calendar.get(Calendar.YEAR),
        day = calendar.get(Calendar.DAY_OF_MONTH)
    )
}

fun Calendar.toMonthYearDay(): MonthDayYear = time.toMonthYearDay()

fun Calendar.toJustMonthYear(): MonthDayYear = time.toMonthYearDay().copy(day = 0)

// 31.08.2022
fun String.convertSiteStringToDate(): MonthDayYear {
    val arrayDate = this.split(".")
    val day = arrayDate[0].toInt()
    val month = arrayDate[1].toInt() -1
    val year = arrayDate[2].toInt()
    return MonthDayYear(day = day, month = month, year = year)
}
