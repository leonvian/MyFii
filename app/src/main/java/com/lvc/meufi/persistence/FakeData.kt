package com.lvc.meufi.persistence

import com.lvc.meufi.home.FiiMonth
import com.lvc.meufi.home.data.DividendPageData
import com.lvc.meufi.model.FiiDividendData
import com.lvc.meufi.model.FiiType
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.utils.toDate

object FakeData {

    fun createFakeDividendPageData(): List<DividendPageData> {
        val list = arrayListOf<DividendPageData>()

        list += createPageData(MonthDayYear(month = 1, year = 2022))
        list += createPageData(MonthDayYear(month = 2, year = 2022))
        list += createPageData(MonthDayYear(month = 3, year = 2022))

        return list
    }

    private fun createPageData(monthYear: MonthDayYear): DividendPageData {
        val dividendList = createDividendDataList(monthYear)

        var sum = 0f
        dividendList.forEach {
            sum += it.dividend * it.amount
        }

        return DividendPageData(
            date = monthYear,
            dividendSum = sum,
            dividendData = dividendList,
            dividendDiffByPreviousMonth = 22.5f
        )
    }

    private fun createDividendDataList(monthYear: MonthDayYear): List<FiiDividendData> {
        val list = arrayListOf<FiiDividendData>()
        list += FiiDividendData(
            fiiCode = "HSAF11",
            amount = 105,
            paymentDate = monthYear,
            dividend = 0.54f,
            type = FiiType.PAPER
        )

        list += FiiDividendData(
            fiiCode = "MGFF11",
            amount = 120,
            paymentDate = monthYear,
            dividend = 0.64f,
            type = FiiType.FOUNDS_OF_FOUNDS
        )

        list += FiiDividendData(
            fiiCode = "HGLG11",
            amount = 112,
            paymentDate = monthYear,
            dividend = 0.94f,
            type = FiiType.BRICK
        )

        list += FiiDividendData(
            fiiCode = "RRCI11",
            amount = 132,
            paymentDate = monthYear,
            dividend = 0.34f,
            type = FiiType.PAPER
        )

        list += FiiDividendData(
            fiiCode = "RGFFU11",
            amount = 101,
            paymentDate = monthYear,
            dividend = 0.84f,
            type = FiiType.BRICK
        )

        list += FiiDividendData(
            fiiCode = "MUUF11",
            amount = 102,
            paymentDate = monthYear,
            dividend = 1.04f,
            type = FiiType.PAPER
        )

        return list
    }

    fun createFakeFiis(): List<FiiMonth> {
        val fiss = arrayListOf<FiiMonth>()
        fiss.add(FiiMonth(1, 2022, 315f))
        fiss.add(FiiMonth(2, 2022, 395f))
        fiss.add(FiiMonth(3, 2022, 435f))
        fiss.add(FiiMonth(4, 2022, 515f))
        fiss.add(FiiMonth(5, 2022, 515f))
        fiss.add(FiiMonth(6, 2022, 595f))
        fiss.add(FiiMonth(7, 2022, 615f))

        fiss.add(FiiMonth(1, 2023, 715f))
        fiss.add(FiiMonth(2, 2023, 895f))
        fiss.add(FiiMonth(3, 2023, 935f))
        fiss.add(FiiMonth(4, 2023, 1015f))
        fiss.add(FiiMonth(5, 2023, 1115f))
        fiss.add(FiiMonth(6, 2023, 1295f))
        fiss.add(FiiMonth(7, 2023, 1315f))

        return fiss
    }

}