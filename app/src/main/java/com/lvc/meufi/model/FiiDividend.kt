package com.lvc.meufi.model

import androidx.room.Embedded
import androidx.room.Entity
import java.util.*

@Entity(primaryKeys = ["fiiCode", "month", "year"])
data class FiiDividend(
    val fiiCode: String,
    val dividend: Float,
    val baseDate: Date? = null,
    @Embedded
    val paymentDate: MonthDayYear,
    val quotation: Float = 0.0f,
    val dividendYieldPercentage: Float = 0.0f,
)