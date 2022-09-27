package com.lvc.meufi.persistence.remote

import com.lvc.meufi.model.FiiDividend
import com.lvc.meufi.utils.convertSiteStringToDate
import com.lvc.meufi.utils.toDate

/**
31.08.2022, 08.09.2022, R$ 86,99, 1,15 %, R$ 1,000,
 */
data class RawDividendData(
    val baseDate: String,
    val paymentDate: String,
    val quotation: String, // price of quote
    val dividendYieldPercentage: String,
    val dividend: String
) {
    constructor(cursor: Iterator<String>) : this(
        baseDate = cursor.next().trim(),
        paymentDate = cursor.next().trim(),
        quotation = cursor.next().trim(),
        dividendYieldPercentage = cursor.next().trim(),
        dividend = cursor.next().trim(),
    )

    //31.08.2022, 08.09.2022, R$ 86,99, 1,15 %, R$ 1,000,
    fun toDividendData(fii: String): FiiDividend {
        return FiiDividend(
            fiiCode = fii,
            dividend = dividend.moneyToFloat(),
            baseDate = baseDate.convertSiteStringToDate().toDate(),
            paymentDate = paymentDate.convertSiteStringToDate(),
            quotation = quotation.moneyToFloat(),
            dividendYieldPercentage = dividendYieldPercentage.percentageToFloat()
        )
    }

    private fun String.moneyToFloat(): Float =
        this.replace("R\$", "")
            .replace(".", "")
            .replace(",", ".").toFloat()

    private fun String.percentageToFloat(): Float =
        this.replace("%", "").replace(",", ".").toFloat()

}