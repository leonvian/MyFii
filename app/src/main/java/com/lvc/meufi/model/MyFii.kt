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
    val updatedAt: MonthDayYear = MonthDayYear.now()
)