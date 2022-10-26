package com.lvc.meufi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Represents an action of buying a fii.
 */
@Entity
data class Buy(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Date,
    val fiiCode: String,
    val unityValue: Float,
    val amount: Int
)
