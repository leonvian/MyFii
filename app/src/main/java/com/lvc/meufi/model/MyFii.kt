package com.lvc.meufi.model

import androidx.room.Embedded
import androidx.room.Entity

/**
 * Represents the amount of FIIs presents on users wallet.
 */
@Entity(primaryKeys = ["fiiCode", "month", "year"])
data class MyFii(
    val fiiCode: String,
    val amount: Int,
    @Embedded
    val updatedAt: MonthDayYear = MonthDayYear.now().copy(day = 0) // Day is irrelevant here
) {

    fun toFiiDividendData() = FiiDividendData(
        fiiCode = fiiCode,
        amount = amount,
        dividend = 0.00f,
        paymentDate = updatedAt,
        type = FiiType.NOT_FILLED
    )

}