package com.lvc.meufi.model

import java.util.concurrent.TimeUnit
import androidx.room.Embedded
import androidx.room.Ignore
import com.lvc.meufi.utils.toDate
import com.lvc.meufi.utils.toMonthYearDay
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Date

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
    val dividendYieldPercentage: Float? = 0.0f,
    val type: FiiType
) {
    val monthYear: MonthDayYear
        get() = paymentDate

    val dividendSum: Float
        get() = amount * dividend

    fun dividendSumToDisplay() = dividendSum.round2Places()

    fun dividendToDisplay() = dividend.round2Places()

    fun daysToBePaid(): Long {
        val today = Date()
        val paymentDateD = paymentDate.toDate()
        return if (paymentDateD.time < today.time) {
            0
        } else {
            val diff = (paymentDateD.time - today.time)
            val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            days
        }
    }

    fun dividendYieldToDisplay(): String? = dividendYieldPercentage?.let {
        "DY % $it"
    }

}

fun Float.round2Places(): String {
    val df = DecimalFormat.getCurrencyInstance()
    df.roundingMode = RoundingMode.DOWN
    val roundOff = df.format(this)
    return roundOff.brMoneyStyle()
}

private fun String.brMoneyStyle(): String =
    replace(".", ",")
        .replace("$", "")
        .replace("R", "")