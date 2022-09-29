package com.lvc.meufi

import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.utils.convertSiteStringToDate
import com.lvc.meufi.utils.toDate
import com.lvc.meufi.utils.toMonth
import com.lvc.meufi.utils.toMonthYearDay
import java.util.Calendar
import org.junit.Assert
import org.junit.Test

class DateUtilsTest {

    @Test
    fun testConversionToMonthYears() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, 1)
        calendar.set(Calendar.YEAR, 2022)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val date = calendar.time

        Assert.assertEquals(Calendar.FEBRUARY, calendar.get(Calendar.MONTH))

        val monthYear = date.toMonthYearDay()
        val calendarMonthYear = Calendar.getInstance()
        calendarMonthYear.time = monthYear.toDate()

        Assert.assertEquals(Calendar.FEBRUARY, calendarMonthYear.get(Calendar.MONTH))

        Assert.assertEquals(calendar.get(Calendar.MONTH), calendarMonthYear.get(Calendar.MONTH))
        Assert.assertEquals(calendar.get(Calendar.YEAR), calendarMonthYear.get(Calendar.YEAR))
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), calendarMonthYear.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun testConversionSiteStringToDate() {
        val siteString = "31.08.2022"
        val monthDayYear = siteString.convertSiteStringToDate()
        Assert.assertEquals(31, monthDayYear.day)
        Assert.assertEquals(7, monthDayYear.month)
        Assert.assertEquals(2022, monthDayYear.year)
    }

    @Test
    fun testConversionSiteStringToDate_december() {
        val siteString = "01.12.2022"
        val monthDayYear = siteString.convertSiteStringToDate()
        Assert.assertEquals(1, monthDayYear.day)
        Assert.assertEquals(11, monthDayYear.month)
        Assert.assertEquals(2022, monthDayYear.year)
    }

    @Test
    fun testConversionSiteStringToDate_January() {
        val siteString = "01.01.2022"
        val monthDayYear = siteString.convertSiteStringToDate()
        Assert.assertEquals(1, monthDayYear.day)
        Assert.assertEquals(0, monthDayYear.month)
        Assert.assertEquals(2022, monthDayYear.year)
    }

    @Test
    fun testMonthsStr() {
        Assert.assertEquals("December", MonthDayYear(month = 11, year = 2022).toMonth())
        Assert.assertEquals("November", MonthDayYear(month = 10, year = 2022).toMonth())
        Assert.assertEquals("October", MonthDayYear(month = 9, year = 2022).toMonth())
        Assert.assertEquals("September", MonthDayYear(month = 8, year = 2022).toMonth())
        Assert.assertEquals("August", MonthDayYear(month = 7, year = 2022).toMonth())
        Assert.assertEquals("July", MonthDayYear(month = 6, year = 2022).toMonth())
        Assert.assertEquals("June", MonthDayYear(month = 5, year = 2022).toMonth())
        Assert.assertEquals("May", MonthDayYear(month = 4, year = 2022).toMonth())
        Assert.assertEquals("April", MonthDayYear(month = 3, year = 2022).toMonth())
        Assert.assertEquals("March", MonthDayYear(month = 2, year = 2022).toMonth())
        Assert.assertEquals("February", MonthDayYear(month = 1, year = 2022).toMonth())
        Assert.assertEquals("January", MonthDayYear(month = 0, year = 2022).toMonth())
    }

}