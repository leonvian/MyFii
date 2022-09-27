package com.lvc.meufi.model

import androidx.room.Embedded
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * It mix the MyFii data and the FiiDividend data.
 * So, it contains the amount of fiis and the dividend of it for a period (month, year).
 */
data class FiiDividendData(
    val fiiCode: String,
    val amount: Int,
    @Embedded
    val paymentDate: MonthDayYear,
    val dividend: Float,
    val type: FiiType
) {
    val monthYear: MonthDayYear
        get() = paymentDate

    val dividendSum: Float
        get() = amount * dividend

    fun dividendSumToDisplay() = dividendSum.round2Places()

    fun dividendToDisplay() = dividend.round2Places()

}

fun Float.round2Places(): String {
    val df = DecimalFormat.getCurrencyInstance()
    df.roundingMode = RoundingMode.DOWN
    val roundOff = df.format(this)
    return roundOff.brMoneyStyle()
}

private fun String.brMoneyStyle(): String =
    replace(".",",").replace("$","")