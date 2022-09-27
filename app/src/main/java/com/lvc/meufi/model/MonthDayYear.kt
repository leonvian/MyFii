package com.lvc.meufi.model

import com.lvc.meufi.utils.toMonthYear
import java.util.*

data class MonthDayYear(val month: Int, val year: Int, val day: Int = 0) : Comparable<MonthDayYear> {

    companion object {

        fun now(): MonthDayYear = Calendar.getInstance().toMonthYear()
    }

    override fun compareTo(other: MonthDayYear): Int {
        return if (this.year == other.year) {
            if(this.month == other.month) {
                this.day.compareTo(other.day)
            } else {
                this.month.compareTo(other.month)
            }
        } else {
             this.year.compareTo(other.year)
        }
    }
}
