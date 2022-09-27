package com.lvc.meufi

import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.persistence.remote.RawDividendData
import com.lvc.meufi.utils.toMonthYear
import org.junit.Assert
import org.junit.Test

class RawDividendDataConverterTest {

    //31.08.2022, 08.09.2022, R$ 86,99, 1,15 %, R$ 1,000,
    @Test
    fun testConvertingDividendDataFromSiteToModel() {
        val fii = "MGFF11"
        val data = "31.08.2022, 08.09.2022, R$ 86,99, 1,15 %, R$ 1,000"
        val iterator = data.split(", ").iterator()
        val raw = RawDividendData(iterator)
        val fiiDividend = raw.toDividendData(fii)

        Assert.assertEquals(1.000f, fiiDividend.dividend)
        Assert.assertEquals(MonthDayYear(month = 7, year = 2022, day = 31), fiiDividend.baseDate?.toMonthYear())
        Assert.assertEquals(MonthDayYear(month = 8, year = 2022, day = 8), fiiDividend.paymentDate)
        Assert.assertEquals(86.99f, fiiDividend.quotation)
        Assert.assertEquals(1.15f, fiiDividend.dividendYieldPercentage)
    }
}